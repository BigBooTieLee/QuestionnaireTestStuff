
import java.io.File;
import java.util.Scanner;

class Questionnaire {

    // variables globales (Ã  la classe)
    static String[] questions; // un tableau de String
    static String[][] options; // une matrice de String (tableau de tableau)
    static int[] answerIndexes;

    static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {

        String quiz = chooseSource();
        // essayer aussi avec chooseSourceV2() qui utilise File au lieu de String

        loadData(quiz);

        runQuiz();

    }

    private static String chooseSource() {
        String[] availableQuizzes = {
                "./Questions.txt",
                "./Better_questions.txt"
        };

        System.out.println("Choisissez un quiz Ã  partir des options suivantes : ");
        for (int i = 0; i < availableQuizzes.length; i++) {
            System.out.println("  "
                    + i
                    + " - "
                    + availableQuizzes[i].replace("./", "").replace(".txt", ""));
        }

        String inputFile = "";
        while (inputFile.equals("")) {

            System.out.print("> ");
            if (userInput.hasNextInt()) {
                int choice = userInput.nextInt();
                if (choice >= 0 && choice < availableQuizzes.length) {
                    inputFile = availableQuizzes[choice];
                } else {
                    System.out.println("Pas un nombre valide.");
                }
            } else {
                System.out.println("Choix invalide. Ã‡a doit Ãªtre un nombre");
                userInput.next(); // entrée Ã  la poubelle
            }

        }

        return inputFile;
    }

    private static String chooseSourceV2() {

        File[] txtFiles = new File(".").listFiles((file) -> file.getName().endsWith(".txt"));
        // cette ligne est inaccessible en ce moment:
        // 1- classe anonyme (new File), 2- expression lambda (() -> ...) pour implémenter une 
        // interface (FileName), 3- utilisation des méthodes de la classe File que nous n'avons 
        // pas étudiée
    
        System.out.println("Choisissez un quiz Ã  partir des options suivantes : ");
        for (int i = 0; i < txtFiles.length; i++) {
            System.out.println("  "
                    + i
                    + " - "
                    + txtFiles[i].getName().replace(".txt", ""));
        }

        File inputFile = null;
        while (inputFile == null) {
            System.out.print("> ");
            if (userInput.hasNextInt()) {
                int choice = userInput.nextInt();
                if (choice >= 0 && choice < txtFiles.length) {
                    inputFile = txtFiles[choice];
                } else {
                    System.out.println("Pas un nombre valide.");
                }
            } else {
                System.out.println("Choix invalide. Ã‡a doit Ãªtre un nombre");
                userInput.next(); // entrée Ã  la poubelle
            }
        }

        return inputFile.toString();
    }

    private static void runQuiz() {
        // Accueillir
        System.out.println("Bienvenue au questionnaire.");

        // Questionnaire
        int numQs = questions.length;

        for (int i = 0; i < numQs; i++) {
            // afficher la question
            System.out.println(questions[i]);

            // afficher les options
            String[] thisQsOptions = options[i];
            for (int j = 0; j < thisQsOptions.length; j++) {
                System.out.println(
                        "  " + j + " - " + thisQsOptions[j]);
            }

            // obtenir et valider le choix de l'utilisateur
            System.out.print("> ");

            if (userInput.hasNextInt()) {
                int answer = userInput.nextInt();
                int correctAnswer = answerIndexes[i];
                if (answer < 0 && answer >= thisQsOptions.length) {
                    System.out.println("Réponse invalide (pas un choix)");
                } else if (answer == correctAnswer) {
                    System.out.println("Bonne réponse");
                } else {
                    System.out.println("Mauvaise réponse");
                }
            } else {
                userInput.next(); // envoyer la réponse Ã  la poubelle
                System.out.println("Réponse invalide (doit Ãªtre un chiffre)");
            }

        }
    }

    private static void loadData(String inputFile) {
        // Lire le fichier
        try (Scanner fileReader = new Scanner(new File(inputFile))) {
            String line;

            // nombre de questions
            line = fileReader.nextLine();
            int numQs = Integer.parseInt(line);

            // initialiser les tableaux
            questions = new String[numQs];
            options = new String[numQs][];
            answerIndexes = new int[numQs];

            // obtenir les données
            for (int i = 0; i < numQs; i++) {
                // ligne vide
                fileReader.nextLine();
                // question
                line = fileReader.nextLine();
                questions[i] = line;
                // choix de réponse
                line = fileReader.nextLine();
                options[i] = line.split(";");
                // bonne réponse
                line = fileReader.nextLine();
                answerIndexes[i] = Integer.parseInt(line);
            }

            fileReader.close();
        } catch (Exception e) {
            System.out.println("Erreur inconnue de lecture de fichier");
            e.printStackTrace();
            System.exit(1); // sinon le programme continue sans les infos dans le fichier
        }
    }
}