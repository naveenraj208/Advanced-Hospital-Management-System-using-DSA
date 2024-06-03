package services;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LogisticRegression {

    private static final int ITERATIONS = 10000;
    private static final double LEARNING_RATE = 0.01;
    private double[] weights;
    private double bias;

    public LogisticRegression(int numFeatures) {
        weights = new double[numFeatures];
        bias = 0.0;
    }

    private double sigmoid(double z) {
        return 1 / (1 + Math.exp(-z));
    }

    public void train(double[][] X, int[] y) {
        int m = X.length;
        int n = X[0].length;

        for (int i = 0; i < ITERATIONS; i++) {
            double[] gradients = new double[n];
            double biasGradient = 0.0;

            for (int j = 0; j < m; j++) {
                double linearModel = bias;
                for (int k = 0; k < n; k++) {
                    linearModel += weights[k] * X[j][k];
                }
                double prediction = sigmoid(linearModel);
                double error = prediction - y[j];

                for (int k = 0; k < n; k++) {
                    gradients[k] += X[j][k] * error;
                }
                biasGradient += error;
            }

            for (int k = 0; k < n; k++) {
                weights[k] -= LEARNING_RATE * gradients[k] / m;
            }
            bias -= LEARNING_RATE * biasGradient / m;
        }
    }

    public int predict(double[] X) {
        double linearModel = bias;
        for (int i = 0; i < weights.length; i++) {
            linearModel += weights[i] * X[i];
        }
        return sigmoid(linearModel) >= 0.5 ? 1 : 0;
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\admin\\Downloads\\archive\\framingham.csv";
        double[][] X = null;
        int[] y = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (X == null) {
                    X = new double[getLineCount(filePath) - 1][values.length - 1];
                    y = new int[X.length];
                    continue; // skip the header
                }
                for (int i = 0; i < values.length - 1; i++) {
                    if (values[i].equals("NA")) {
                        X[row][i] = 0.0; // Replace "NA" with 0.0
                    } else {
                        X[row][i] = Double.parseDouble(values[i]);
                    }
                }
                y[row] = Integer.parseInt(values[values.length - 1]);
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (X == null || y == null) {
            System.out.println("Failed to load data.");
            return;
        }

        LogisticRegression model = new LogisticRegression(X[0].length);
        model.train(X, y);

        // User input for prediction
        Scanner scanner = new Scanner(System.in);
        double[] newSample = new double[X[0].length];

        System.out.println("Enter the values for prediction:");

        System.out.print("Enter 1 if male, 0 if female: ");
        newSample[0] = scanner.nextInt();

        System.out.print("Enter age: ");
        newSample[1] = scanner.nextInt();

        System.out.print("Enter years of education: ");
        newSample[2] = scanner.nextInt();

        System.out.print("Enter 1 if current smoker, 0 if not: ");
        newSample[3] = scanner.nextInt();

        System.out.print("Enter number of cigarettes per day: ");
        newSample[4] = scanner.nextInt();

        System.out.print("Enter 1 if on blood pressure medication, 0 if not: ");
        newSample[5] = scanner.nextInt();

        System.out.print("Enter 1 if had stroke, 0 if not: ");
        newSample[6] = scanner.nextInt();

        System.out.print("Enter 1 if hypertensive, 0 if not: ");
        newSample[7] = scanner.nextInt();

        System.out.print("Enter 1 if diabetic, 0 if not: ");
        newSample[8] = scanner.nextInt();

        System.out.print("Enter total cholesterol: ");
        newSample[9] = scanner.nextInt();

        System.out.print("Enter systolic blood pressure: ");
        newSample[10] = scanner.nextInt();

        System.out.print("Enter diastolic blood pressure: ");
        newSample[11] = scanner.nextInt();

        System.out.print("Enter body mass index (BMI): ");
        newSample[12] = scanner.nextDouble();

        System.out.print("Enter heart rate: ");
        newSample[13] = scanner.nextInt();

        System.out.print("Enter blood glucose level: ");
        newSample[14] = scanner.nextInt();

        int prediction = model.predict(newSample);
        if (prediction == 1) {
            System.out.println("You are predicted to have cardiovascular disease. Consult a doctor soon.");
        } else {
            System.out.println("You are not diagnosed with cardiovascular disease.");
        }
        System.out.println("Note: This is a model prediction and should not be used for medical diagnosis. Please consult a healthcare professional for personalized advice.");
    }

    private static int getLineCount(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int lines = 0;
            while (br.readLine() != null) lines++;
            return lines;
        }
    }
}
