package org.example;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String username="";

        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("https://api.github.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        // Create an instance of our GitHub API interface.
        GitHub github = retrofit.create(GitHub.class);

        boolean running=true;
        while(running){
            System.out.print("Please enter the GitHub username (type exit to quit)\n");
            username = scanner.nextLine();
            try{
                if(username.equalsIgnoreCase("exit")){
                    System.out.println("Exiting the system... Goodbye!");
                    running=false;
                    break;
                }
                else{
                    // Create a call instance for looking up Retrofit contributors.
                    Call<List<Repo>> call = github.repos(username);
                    // Fetch and print a list of the contributors to the library.
                    List<Repo> repos = call.execute().body();
                    for (Repo repo : repos) {
                        System.out.println("Name: "+repo.name + " Description: " + repo.description+" Stargarzers: "+repo.stargarzers_count);
                    }
                }

            }catch(NullPointerException e){
                System.out.println("Username not found!");
            }
        }
        scanner.close();
    }
}