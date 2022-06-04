package br.com.up.main;

import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import br.com.up.networking.HTTPRequest;
import br.com.up.printing.Printing;

import java.util.concurrent.TimeUnit;

public class Main {

    // AMount of pokemon to be displayed

    private static final int pokemonCount = 151;

    public static void main(String[] args) throws Exception {

        // Link to the API + the number of pokemon to be returned

        String apiUrl = "https://pokeapi.co/api/v2/pokemon?limit=" + pokemonCount;

        // Requesting the API

        JSONObject apiResponse = new HTTPRequest().requestGetMethod(apiUrl);

        // If the API is not responding, the program will exit

        if (apiResponse == null) {

            throw new Exception("API response is null");

        }

        Scanner scanner = new Scanner(System.in);

        // Saying hello and explaning the programns purpose and usabillity

        Spacing(1);
        Welcome();
        Spacing(3);

        // Main loop to make possible for many pokemon requests

        outerloop: while (true) {

            // Getting the pokemon array from the API response

            JSONArray pokemonList = apiResponse.getJSONArray("results");

            // Definining the pokemon object variable and name variable

            JSONObject pokemon;
            String name;

            // Displaying the pokemon list

            for (int i = 0; i < pokemonCount; i++) {

                pokemon = pokemonList.getJSONObject(i);
                name = pokemon.getString("name");
                System.out.println((i + 1) + ") " + name);

            }

            Spacing(3);

            // Asking the user for a pokemon

            int selection;

            outer: while (true) {

                System.out.print("Select a pokemon's number: ");
                selection = scanner.nextInt() - 1;
                pokemon = pokemonList.getJSONObject(selection);

                if (selection > pokemonCount || selection < 0) {

                    System.out.println("Invalid pokemon number.");
                    continue outer;

                }

                break outer;

            }

            Spacing(1);

            // Getting the pokemon's name

            String pokemonUrl = pokemon.getString("url");
            name = pokemon.getString("name");

            // Displaying the selected pokemon's name

            System.out.println("You selected: [" + name + "]");
            Spacing(1);

            // Getting the pokemon's data

            JSONObject pokemonResponse = new HTTPRequest().requestGetMethod(pokemonUrl);
            JSONArray abilities = pokemonResponse.getJSONArray("abilities");
            JSONArray forms = pokemonResponse.getJSONArray("forms");
            JSONArray game_indices = pokemonResponse.getJSONArray("game_indices");
            JSONArray moves = pokemonResponse.getJSONArray("moves");
            JSONArray stats = pokemonResponse.getJSONArray("stats");
            JSONArray types = pokemonResponse.getJSONArray("types");
            int base_experience = pokemonResponse.getInt("base_experience");
            int height = pokemonResponse.getInt("height");
            int id = pokemonResponse.getInt("id");
            int order = pokemonResponse.getInt("order");
            int weight = pokemonResponse.getInt("weight");

            // Getting the pokemon's sprites

            String imageURL = pokemonResponse.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_default");

            Printing printing = new Printing();

            printing.convertToAscii(imageURL);

            Spacing(1);

            // Displaying the pokemon's first bits of data

            System.out.printf("Base Experience: [%d]\n\n", base_experience);
            System.out.printf("Height: [%d]\n\n", height);
            System.out.printf("ID: [%d]\n\n", id);
            System.out.printf("Order: [%d]\n\n", order);
            System.out.printf("Weight: [%d]\n", weight);

            // Displaying the pokemon's types

            Spacing(1);
            System.out.print("Type: ");

            for (int i = 0; i < types.length(); i++) {

                JSONObject type = types.getJSONObject(i);
                String typeName = type.getJSONObject("type").getString("name");
                System.out.printf("[%s]  ", typeName);

            }

            // Displaying the pokemon's stats

            Spacing(2);
            System.out.print("Stats: ");

            for (int i = 0; i < stats.length(); i++) {

                JSONObject stat = stats.getJSONObject(i);
                int stat_base_stat = stat.getInt("base_stat");
                String stat_name = stat.getJSONObject("stat").getString("name");
                System.out.printf("[%s: %d]  ", stat_name, stat_base_stat);

            }

            // Displaying the pokemon's abilities

            Spacing(2);
            System.out.print("Ability: ");

            for (int i = 0; i < abilities.length(); i++) {

                JSONObject ability = abilities.getJSONObject(i);
                String abilityName = ability.getJSONObject("ability").getString("name");
                System.out.printf("[%s]  ", abilityName);

            }

            // Displaying the pokemon's forms

            Spacing(2);
            System.out.print("Form: ");

            for (int i = 0; i < forms.length(); i++) {

                JSONObject form = forms.getJSONObject(i);
                String formName = form.getString("name");
                System.out.printf("[%s]  ", formName);

            }

            // Displaying the pokemon's moves

            Spacing(2);
            System.out.print("Move: ");

            for (int i = 0; i < moves.length(); i++) {

                JSONObject move = moves.getJSONObject(i);
                String moveName = move.getJSONObject("move").getString("name");
                System.out.printf("[%s]  ", moveName);

            }

            // Displaying the pokemon's game indices and game versions

            Spacing(2);
            System.out.print("Game Index: ");

            for (int i = 0; i < game_indices.length(); i++) {

                JSONObject game_index = game_indices.getJSONObject(i);
                int game_index_game_index = game_index.getInt("game_index");
                String game_index_version = game_index.getJSONObject("version").getString("name");
                System.out.printf("[%d (version %s)]  ", game_index_game_index, game_index_version);

            }

            // Asking if the user wnats to see another pokemon's data, if yes the program
            // will continue, if no the program will exit

            Spacing(4);
            System.out.println("Pick another pokemon? (y/n)");

            outer: while (true) {

                String answer = scanner.next();

                if (answer.equals("y")) {

                    Spacing(1);
                    break outer;

                } else if (answer.equals("n")) {

                    break outerloop;

                } else {

                    System.out.println("Invalid answer.");
                    continue outer;

                }

            }

        }

        scanner.close();
        return;

    }

    // Method to display the program's welcome message

    private static void Welcome() {

        System.out.println("Welcome to the Pokemon Pokedex!");
        System.out.println("This program will show you the first " + pokemonCount + " pokemon.");
        System.out.println("You can then select a specific pokemon by it's number. (ex 1)");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return;

    }

    // Method to jump x amount of lines

    private static void Spacing(int x) {

        for (int i = 0; i < x; i++) {
            System.out.println("");
        }
        return;

    }

}
