import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene; 
import javafx.scene.control.Label;
import javafx.scene.control.TextField; 
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.StringTokenizer;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
public class RandomNumberDemo extends Application
{
	private Label promtLabel = new Label();
	private TextField text = new TextField();
	private Label reportLabel = new Label();
	private Label endLabel = new Label();
	private Label totalLabel = new Label();
	private Label numbers = new Label();
	private Label entered = new Label();
	private int first;
	private int second;
	private int totalFreq = 0;
	private int count = 1;
	private int len = 0;
	private int frequency = 0;
	private int index = 0;
	private Label feedBack = new Label();
	private ArrayList<Integer> rand;
	private VBox vbox;
	private VBox vboxRoot;
	private Scene scene;
	private ArrayList<Integer> arraylist = new ArrayList<>();
	private ArrayList<Integer> list;
	public static void main(String[] args)
	{
		launch(args);
	}
	@Override
	public void start(Stage primaryStage)
	{
		final double SCENE_WIDTH = 700.0;
		final double SCENE_HEIGHT = 600.0;
		GameHandler game = new GameHandler();
		Label introLabel = new Label("The game you are about to play is entering "+
		                        "a number to see if you gussed it right.");
		introLabel.setStyle( "-fx-font-size: 12pt");
		Button button = new Button("Start playing ");
		button.setStyle( "-fx-font-size: 14pt");
		button.setOnAction(event ->
		{
			frequency = 0;
			totalFreq = 0;
			len = 0;
			list = new ArrayList<>();
			totalLabel.setText("your total point is: " + totalFreq);
			totalLabel.setStyle( "-fx-font-size: 12pt");
			endLabel.setText("");
					numbers.setText(" ");
					entered.setText(" ");
					reportLabel.setText(" ");
					feedBack.setText(" ");
			rand = new ArrayList<>(15);
			Random random = new Random();
			first = random.nextInt(950);
			second = first + 50;
			for(int i = 0; i < 15; i++)
			{
				rand.add(game.randomNumber(first, second));
			}
			System.out.println(game.randSub());
			System.out.println(rand + " ");
			promtLabel.setText("Enter number between " + first + "  and  " + second);
			promtLabel.setStyle( "-fx-font-size: 12pt");
		});
		Button exitButton = new Button("Exit");
		exitButton.setStyle( "-fx-font-size: 14pt");
		exitButton.setOnAction(event ->
		{
			System.exit(0);
		});
		HBox hbox1 = new HBox(10, button, exitButton);
		hbox1.setPadding(new Insets(30));
		vbox = new VBox(10, text, feedBack, reportLabel, endLabel, numbers, entered);
		vboxRoot = new VBox(10, introLabel, hbox1, promtLabel, vbox, totalLabel);
		vboxRoot.setAlignment(Pos.TOP_CENTER);
		vboxRoot.setPadding(new Insets(40));
		scene = new Scene(vboxRoot, SCENE_WIDTH, SCENE_HEIGHT);
		scene.setOnKeyPressed(event ->
		{
			if(event.getCode() == KeyCode.ENTER)
			{
				int inputs = 0;
				int input = 0;
					input = game.acceptInput();
					inputs = input;
					list.add(input);
					input = game.checkMultipleInput(list, input);
					arraylist.add(input);
					index = arraylist.indexOf(input);
					frequency = game.frequency(rand, input);
					len++;
					if(index < 14)
					{
						totalFreq += frequency;
						feedBack.setText("There are " + frequency + " times of " + inputs);
						feedBack.setStyle( "-fx-font-size: 12pt");
						totalLabel.setText("your total point is: " + totalFreq);
						totalLabel.setStyle( "-fx-font-size: 12pt");
					}
					if(index > 0)
					{
						if(game.frequency(rand, arraylist.get(index)) > 0 && game.frequency(rand, arraylist.get(index - 1)) > 0)
						{
							count++;
							totalFreq = totalFreq * count;
							totalLabel.setText("your total point is: " + totalFreq);
							totalLabel.setStyle( "-fx-font-size: 12pt");
						}
						else
						{
							count = 1;
						}
					}
					if(input > second || input < first)
					{
						feedBack.setText("The number you Entered is outside of range.");
						feedBack.setStyle( "-fx-font-size: 12pt");
						index--
						totalLabel.setText("your total point is: " + totalFreq);
						totalLabel.setStyle( "-fx-font-size: 12pt");
					}
					if(input == -1)
					{
						feedBack.setText("you have entered " + inputs + " before");
						feedBack.setStyle( "-fx-font-size: 12pt");
						index--;
						totalLabel.setText("your total point is: " + totalFreq);
						totalLabel.setStyle( "-fx-font-size: 12pt");
					}
					reportLabel.setText("your point is " + totalFreq);
					reportLabel.setStyle( "-fx-font-size: 12pt");
					if(index == 14)
					{
						endLabel.setText("The Game is Over!");
						endLabel.setStyle( "-fx-font-size: 12pt");
						totalLabel.setText("your total point is: " + totalFreq);
						totalLabel.setStyle( "-fx-font-size: 12pt");
						numbers.setText("The numbers were: " + rand);
						numbers.setStyle( "-fx-font-size: 12pt");
						entered.setText("your guess is: " + list);
						entered.setStyle( "-fx-font-size: 12pt");
					}
					if(index > 14)
					{
						endLabel.setText("");
						numbers.setText(" ");
						entered.setText(" ");
						reportLabel.setText(" ");
						feedBack.setText(" ");
						totalLabel.setText("your total point is: " + totalFreq);
						totalLabel.setStyle( "-fx-font-size: 12pt");
						promtLabel.setText("\nthe game is done for this cycle. " +
											"\nclick Start Playing button to start new game or" +
											"\nexit button to close the window.");
						promtLabel.setStyle( "-fx-font-size: 12pt");
					}
			}
		});
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	class GameHandler
	{
		public int acceptInput()
		{
			int input = 0;
			StringTokenizer token = new StringTokenizer(text.getText());
			while(token.hasMoreTokens())
			{
					input = Integer.parseInt(token.nextToken());
					text.clear();
			}
			return input;
		}
		/*
		this method checks for mulpliple entry of the same number.
		*/
		public int checkMultipleInput(ArrayList<Integer> inputArray, int input)
		{
			for(int i = 0; i < inputArray.size() - 1; i++)
			{
				if(inputArray.size() > 1)
				{
					if(input == inputArray.get(i))
					{
						input = -1;
					}
				}
				else
				{
					input = input;
				}
			}
			return input;
		}
		public int randomNumber(int fir, int sec)
		{
			Random random = new Random();
			int number = 0;
			number = random.nextInt(sec);
			while(number <= fir)
			{
				number++;
				number = number + randSub();
			}
			return number;
		}
		public int randSub()
		{
			Random random = new Random();
			return random.nextInt(50);
		}
		public int frequency(ArrayList<Integer> random, int input)
		{
			int freq = 0;
			for(int i = 0; i < 15; i++)
			{
				if(random.get(i) == input)
					freq++;
			}
			return freq;
		}
		public int addFreq(int freq)
		{
			int total = 0;
			total += freq;
			return total;
		}
	}
}