import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;
import java.util.stream.Stream;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;

public class Calculator extends JFrame implements ActionListener {
     //Containers
     private static JPanel containerPanel = new JPanel();
     private static JPanel outputPanel = new JPanel();
     private static JPanel inputPanel = new JPanel();

     private static JTextField outputLabel = new JTextField();

     //Functional and extra buttons (Instantiating default buttons)
     private static JButton clearBtn = new JButton("C");
     private static JButton equalsBtn = new JButton("=");
     private static JButton delBtn = new JButton("⬅"); // "\u2190"
     private static JButton percentageBtn = new JButton("%");

     private static JButton decBtn = new JButton(".");

     private final static JButton[] funcBtnArray = {clearBtn, equalsBtn, delBtn, percentageBtn};

     //Number buttons (Declaration)
     private static JButton btn0;
     private static JButton btn1;
     private static JButton btn2;
     private static JButton btn3;
     private static JButton btn4;
     private static JButton btn5;
     private static JButton btn6;
     private static JButton btn7;
     private static JButton btn8;
     private static JButton btn9;

     private final static JButton[] numBtnArray = {btn0, btn1, btn2,
                                                   btn3, btn4, btn5,
                                                   btn6, btn7, btn8,
                                                   btn9};

     private final static HashMap<JButton, String> btnMap = new HashMap<>();

     //Operator buttons (Instantiating default buttons)
     private static JButton plusBtn = new JButton("+");
     private static JButton minusBtn = new JButton("-");
     private static JButton mulBtn = new JButton("×"); // "\u00D7"
     private static JButton divideBtn = new JButton("÷"); // "\u00F7"

     private final static JButton[] operatorBtnArray = {plusBtn, minusBtn, mulBtn, divideBtn};

     private final static JButton[] extraBtnArray = {percentageBtn, decBtn};

     private static JButton[] uniBtnArray;

     private static String equation = "";

     Calculator() {

          //Creates the GUI window
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          this.setSize(700, 700);
          this.setResizable(false);
          this.getContentPane().setBackground(Color.decode("#191919"));

          containerPanel.setLayout(new BorderLayout(0, 10));
          containerPanel.setBackground(Color.decode("#191919"));

          //Input & Output container configuration
          inputPanel.setBackground(Color.decode("#191919"));
          outputPanel.setBackground(Color.decode("#191919"));

          inputPanel.setLayout(new GridLayout(5, 4, 10, 10));

          //Calculator main Output
          outputLabel.setEditable(false);
          outputLabel.setFont(new Font("", Font.ITALIC, 40));
          outputLabel.setFocusable(false);
          outputLabel.setPreferredSize(new Dimension(500, 80));
          outputLabel.setHorizontalAlignment(JTextField.CENTER);

          decBtn.setBackground(Color.decode("#282828"));;

          //Instantiating the number buttons dynamically
          for(Integer index = 0; index < numBtnArray.length; index++) {
               numBtnArray[index] = new JButton(index.toString());
               numBtnArray[index].setBackground(Color.decode("#282828"));
          }

          //Settings the colors for buttons dynamically
          for(JButton btn : operatorBtnArray) {
               btn.setBackground(Color.decode("#f48128"));
          }

          for(JButton btn : funcBtnArray) {
               btn.setBackground(Color.decode("#f8a1ff"));
          }

          //Creates a new array with the operator, number and extra buttons arrays combined
          uniBtnArray = Stream.concat(Stream.concat(
                                      Stream.of(numBtnArray),
                                      Stream.of(operatorBtnArray)),
                                      Stream.of(extraBtnArray)
                                     ).toArray(JButton[]::new);

          for(JButton btn : uniBtnArray) {
               btnMap.put(btn, btn.getText());
          }

          //Layout of the buttons in an array
          final JButton[][] btnLayoutArray = {
               {clearBtn, percentageBtn, delBtn, plusBtn},
               {numBtnArray[7], numBtnArray[8], numBtnArray[9], minusBtn},
               {numBtnArray[4], numBtnArray[5], numBtnArray[6], mulBtn},
               {numBtnArray[1], numBtnArray[2], numBtnArray[3], divideBtn},
               {numBtnArray[0], decBtn, equalsBtn}
          };

          //Adding buttons according to the layout dynamically
          for(JButton[] btnArray : btnLayoutArray) {
               for(JButton btn : btnArray) {
                    btn.addActionListener(this);

                    btn.setFocusable(false);
                    btn.setForeground(Color.WHITE);
                    btn.setFont(new Font("", Font.BOLD, 40));
                    btn.setPreferredSize(new Dimension(80, 80));
                    inputPanel.add(btn);
               }
          }

          outputPanel.add(outputLabel);

          containerPanel.add(outputPanel, BorderLayout.NORTH);
          containerPanel.add(inputPanel, BorderLayout.CENTER);

          this.add(containerPanel);

          this.setLayout(new FlowLayout(FlowLayout.CENTER));
          this.setVisible(true);
     }

     void displayCharacter(String character) {
          equation += character;
          outputLabel.setText(equation);
     }

     void deleteCharacter() {
          int eqLength = equation.length();
          eqLength--;

          if(eqLength >= 0) {
               equation = equation.substring(0, eqLength);
          }
          else {
               equation = "";
          }

          outputLabel.setText(equation);
     }

     void clearOutput() {
          equation = "";
          outputLabel.setText(equation);
     }

     void calculateEq() {
          //Replaces illegal characters for eval() in Java-Script
          equation = equation.replace("×", "*").
                              replace("÷", "/").
                              replace("%", "/100");

          //Creates threads that write, execute and read the equated result
          new Thread(() -> {
               Writer.writeEquation(equation);
               Executer.executeEvaluator();
               equation = Reader.readResult();

               SwingUtilities.invokeLater(() -> {
                    outputLabel.setText(equation);
               });
          }).start();
     }

     @Override
     public void actionPerformed(ActionEvent event) {
          Object source = event.getSource();

          for(JButton btn : uniBtnArray) {
               if(source == btn) {
                    //Gets the assigned value from the hash map
                    displayCharacter(btnMap.get(btn));
               }
          }

          if(source == delBtn) {
               deleteCharacter();
          }
          else if(source == clearBtn) {
               clearOutput();
          }
          else if(source == equalsBtn) {
               calculateEq();
          }
     }
}