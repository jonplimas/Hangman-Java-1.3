/*
 * AUTHOR NAME: Jon Limas
 * DATE CREATED: 11/23/2019
 * LAST UPDATED ON: 11/27/2019
 * CPSC 223J FINAL PROJECT: Hangman
 * DESCRIPTION: This Program explores the use of JFrame and GUI in order to 
 *              create a fully-operational Hangman Game. 
 */
package javaproject1;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Hangman extends JFrame implements ActionListener {
    String phrase;          //the word to be guessed, pulled out of WordBank(s)
    String word;            //used to convert phraseArray and setText for guessThis
    char[] phraseArray;     //used to add spaces in between phrase, for formatting
    JLabel guessThis;       //holds "_ _ _ _ _ " guess for game, see GAMEBOTTOM**
     
    int wrongCount = 0;    //running count for incorrect guesses, indicator for a loss
    int rightCount = 0;    //running count for correct guesses, used to indicate a win
    
    //Easy Word Bank
    private static String[] easyWords = {
      "apple", "bear", "cat", "dog", "elbow", "follow", "glass", "heat", "goat",
      "soap", "towel", "paper", "game", "idea", "sock", "line", "wood", "hair",
      "cold", "bag", "oven", "ice", "feet", "egg", "party", "box", "kind", "light",
      "note", "bottle", "rice", "frog", "bread", "much", "light", "only", "long", 
      "short", "myself", "said", "catch", "takes", "waved", "prey", "help", "please",
      "great", "left", "easy", "hard", "medium", "rare", "sea", "drink", "swim", 
      "while", "for", "root", "aim", "draw", "mean", "mode", "middle", "zebra",
      "never", "always", "time", "fire", "artic", "car", "area", "mile", "inch",
      "boil", "hair", "gate", "skin", "puny", "rail", "job", "crab", "sack", "chip",
      "fly", "gift", "yes", "worm", "west", "east", "north", "south","day", "night"      
    };
    
    //Intermediate Word Bank
    private static String[] intermediateWords = {
      "mercy", "oasis", "complete", "pizza", "gigantic", "width", "height", "biology",
      "chemical", "notebook", "orange", "argue", "blemish", "crafty", "dragon", "warning",
      "pardon","excuse", "physical", "citizen", "oxygen", "rough", "thumb", "trouble",
      "suddenly", "afraid", "explain", "sleigh", "favorite", "radio", "daylight",
      "eager","universe", "beautiful", "communicate", "provide", "lyrics", "drama",
      "father", "eggplant", "dinosaur", "cellphone", "onion", "diagram", "overdue",
      "guardian", "harbor", "telephone", "swordsman", "cereal", "pattern", "birth",
      "heavy", "value", "valor", "warrant", "jelly", "absolute", "nickel", "quarter",
      "juice", "foreward", "blank", "raisin", "chicken", "hinge", "martyr", "plastic",
      "glove", "under", "original", "knife", "donut","without", "livid", "subject",
      "justice", "electric", "young", "famous", "rocket", "celtic", "sport", "helmet"
    };
    
    //Hard Word Bank
    private static String[] hardWords = {
      "banjo", "awkward", "bagpipes", "bungler", "croquet", "crypt", "dwarves",
      "fervid", "gazebo", "gypsy", "haiku","haphazard", "hyphen", "horizen","ivory",
      "jiffy", "jukebox", "kayak", "kiosk", "klutz", "memento", "mystify",
      "notarize", "pajama", "phlegm", "pixel", "polka", "quadricep", "equip",
      "rhythmic", "rougue", "swivel", "twelfth", "unzip", "waxy", "yacht", "zealous",
      "zombie", "jeapordize", "whereabouts", "ketchup", "tyrant", "elbow", "gourmet",
      "aquarium", "slithery", "robust", "analytics", "galavant","myriad", "eclipse",
      "helix", "jargon", "irony", "dynamics", "vortex", "plaque", "atrium", "embezzle",
      "porcelain", "lawsuit", "trailblazer", "sovereign", "courtyard", "oxford",
      "cervix", "questionable", "phlanges", "networking", "umbrella", "inquire",
      "axioms", "complexity", "savior", "rivalry", "organize", "axotl"
    };
    
    //Home Page Contents
    //empty JLabels used to maintain GridLayout
    JPanel homeCard;
        JLabel topLeft = new JLabel("");
        JLabel topMiddle = new JLabel("H A N G M A N !", SwingConstants.CENTER);
        JLabel topRight = new JLabel("");
        
        JLabel twoLeft = new JLabel("");
        JLabel twoMiddle = new JLabel("Choose Difficulty",SwingConstants.CENTER);
        JLabel twoRight = new JLabel("");
        
        JButton easy = new JButton("EASY");
        JButton intermediate = new JButton("INTERMEDIATE");
        JButton hard = new JButton("HARD");
        
        JLabel bottomLeft = new JLabel("");
        JLabel bottomMiddle = new JLabel("");
        JLabel bottomRight = new JLabel("Created By: Jon P. Limas");
    
    //Gameplay Page Contents
    JPanel gameCard;
    JPanel gameTop;
        JPanel gameGrid;
        JLabel[][] drawing = new JLabel[30][20];     //Grid for hangman drawing 
    JPanel gameBottom;
        JPanel gbCenter;
        JPanel[][] abcPanel = new JPanel[4][7];
        JButton[][] abcButtons = new JButton[4][7];

    //Declared an instance of a CardLayout for constructor
    CardLayout myCardLayout = new CardLayout();
    
    
    //CONSTRUCTOR
    public Hangman(){
        super("Hangman");
        
        //Frame Settings
        setSize(475,570);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(myCardLayout);
        
        //Home Page Settings
        homeCard = new JPanel();
        homeCard.setLayout(new GridLayout(4,3,5,5));
        homeCard.setBackground(Color.ORANGE);
        
        topMiddle.setFont(new Font("Arial", Font.BOLD, 20));
        twoMiddle.setFont(new Font("Arial", Font.BOLD, 14));
        easy.setToolTipText("A solid 3rd Grade Reading Level...");
        intermediate.setToolTipText("For the average High School Dropout...");
        hard.setToolTipText("The words your grandma makes up for Scrabble...");
        
        //Adding HomeCard Contents to Frame
        homeCard.add(topLeft);
        homeCard.add(topMiddle);
        homeCard.add(topRight);
        homeCard.add(twoLeft);
        homeCard.add(twoMiddle);
        homeCard.add(twoRight);
        homeCard.add(easy);
        homeCard.add(intermediate);
        homeCard.add(hard);
        homeCard.add(bottomLeft);
        homeCard.add(bottomMiddle);
        homeCard.add(bottomRight);
        add(homeCard, "HomeCard");
        
        //HomeCard ActionListeners
        easy.addActionListener(this);
        intermediate.addActionListener(this);
        hard.addActionListener(this);
        
        
        //GamePage Settings
        gameCard = new JPanel();
        gameCard.setLayout(new GridLayout(2,1,2,2));
        gameTop = new JPanel();
        gameTop.setLayout(new BorderLayout());
        gameGrid = new JPanel();
        gameGrid.setLayout(new GridLayout(30,20,2,2));
        gameGrid.setBackground(Color.ORANGE);
        
        gameBottom = new JPanel();
        gameBottom.setLayout(new BorderLayout());
        gbCenter = new JPanel();
        gbCenter.setLayout(new GridLayout(4,7,1,1));
        gbCenter.setBackground(Color.GREEN);
        
        //Position the blanks for phrase
        guessThis = new JLabel("_", SwingConstants.CENTER);
        guessThis.setFont(new Font("Arial", Font.BOLD, 40));
        
        
        //GAMETOP: Shows Hangman Drawing
        for(int i = 0; i < 30; i++){
            for(int j = 0; j < 20; j++){
                drawing[i][j] = new JLabel(" ", SwingConstants.CENTER);
                drawing[i][j].setFont(new Font("Arial", Font.BOLD, 12));
                gameGrid.add(drawing[i][j]);
            }
        }
        
        //Initial Drawing Created: noose and hanger
        for(int i = 0; i < 2; i++){             //Top Bar arm that holds rope    
            for(int j = 9; j < 20; j++){
                drawing[i][j].setText("H");
            }
        }
        for(int i = 2; i < 30; i++){            //pole of hanger
            for(int j = 18; j < 20; j++){
                drawing[i][j].setText("H");
            }
        }
        for(int j = 3; j < 20; j++){           //base of hanger
            drawing[29][j].setText("H");
        }
        for(int i = 0; i < 8; i++){           //rope
            drawing[i][9].setText("H");
        }
        drawing[4][8].setText("H");         //rope knots
        drawing[4][10].setText("H");
        drawing[5][8].setText("H");
        drawing[5][10].setText("H");
        

        //GAMEBOTTOM: shows letters
        gameBottom.add(guessThis,BorderLayout.NORTH);
        int letterCount = 0;    //used to index current letter
        
        //Nested for loop used to set up all game buttons 
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 7; j++){
                abcButtons[i][j] = new JButton(""+(char)(65+letterCount));
                abcButtons[i][j].addActionListener(this);
                gbCenter.add(abcButtons[i][j]);
                letterCount++;
            }
        }
        abcButtons[3][5].setText("Home");
        abcButtons[3][5].setToolTipText("WARNING: all progress of current game will be lost");
        abcButtons[3][6].setText("Quit");
        abcButtons[3][6].setToolTipText("To EXIT out of program");
        
        //Adding Game Panels together & to GameCard 
        gameTop.add(gameGrid, BorderLayout.CENTER);
        gameCard.add(gameTop);
        gameBottom.add(gbCenter, BorderLayout.CENTER);
        gameCard.add(gameBottom);
        add(gameCard, "GameCard");
    }
    //------------------------------END OF CONSTRUCTOR--------------------------

    
    //METHODS
    
    //Method to draw a body part onto hangman
    //Number of wrong guesses determines which body part is drawn
    public void drawHangman(int numWrong){
        if(numWrong == 1){                         //1st wrong, draw head
            for(int i = 8; i < 14;i++){
                for(int j = 8; j < 11; j++){
                    drawing[i][j].setText("J...");
                }
            }
        }else if(numWrong == 2){                   //2nd wrong, draw torso
            for(int i = 14; i < 21; i++){
                drawing[i][9].setText("J...");
            }
        }else if(numWrong == 3){                   //3rd wrong, draw right arm
            for(int i = 15; i < 19; i++){
                for(int j = 10; j < 13;j++){
                    if((i-5) == j){
                       drawing[i][j].setText("J...");
                    }
                } 
            }
        }else if(numWrong == 4){                   //4th wrong, draw left arm
            drawing[17][6].setText("J...");
            drawing[16][7].setText("J...");
            drawing[15][8].setText("J...");
            
        }else if(numWrong == 5){                   //5th wrong, draw right leg
            for(int i = 21; i < 27; i++){
                drawing[i][10].setText("J...");
            }
        }else if(numWrong == 6){       //6 wrong, draw left leg
            for(int i = 21; i < 27; i++){
                drawing[i][8].setText("J...");
            }
        }
        
    }
    
    
    //Method to initialize blanks for game
    //parameter is the word to be guessed
    public void placeBlanks(String str){
        phraseArray = new char[str.length()*2];
            for(int i = 0; i < str.length()*2; i+=2){
                phraseArray[i] = '_';
                phraseArray[i+1] = ' ';
            }
            word = new String(phraseArray); 
            guessThis.setText(word);
    }
    
    //Method checks if letter guessed is in phrase and updates guess, returns bool.
    //IF TRUE, iterates through to place letter accordingly.
    //IF FALSE, nothing.
    public boolean checkGuess(char ch){
        if(phrase.indexOf(ch) >= 0){   //if within phrase
            for(int i = 0; i < phrase.length();i++){ //iterates through to check,
                if(phrase.charAt(i) == ch ){        //if the target char is @ index
                    phraseArray[i*2] = ch;          //add char to appropriate spot
                    rightCount++;                   //increment amt letters correct
                }
            }
            word = new String(phraseArray);         //update User's guess
            guessThis.setText(word);
            return true;
        }
        return false;
    }
    
    
    //Method to check if player has guessed wrong 6 times
    public boolean youLose(){
        if(wrongCount >5){
            return true;
        } else {
            return false;
        }
    }
    
    
    //Method to reset Hangman Game
    public void newGame(){
        //resets counts for guesses
        wrongCount = 0;
        rightCount = 0;
        
        //Clears HangMan drawing
        for(int i = 8; i < 27; i++){
            for(int j = 6; j < 13; j++){
                drawing[i][j].setText(" ");
            }
        }
        //Resets all buttons for letters
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 7; j++){
                abcButtons[i][j].setEnabled(true);
            }
        }
        //ensures reset of guess color, even after a win/loss
        guessThis.setForeground(Color.BLACK);
    }
    //--------------------------END OF METHODS----------------------------------

    
    //ACTIONS FOR BUTTONS
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        //HOME BUTTONS
        //Game initialized upon choosing Level of Difficulty
        if(source == easy){
            phrase = easyWords[(int)(Math.random()*easyWords.length)];
            placeBlanks(phrase);
            newGame();
            myCardLayout.show(getContentPane(), "GameCard");
        }else if(source == intermediate){
            phrase = intermediateWords[(int)(Math.random()*intermediateWords.length)];
            placeBlanks(phrase);
            newGame();
            guessThis.setForeground(Color.BLACK);
            myCardLayout.show(getContentPane(), "GameCard");
        }else if(source == hard){
            phrase = hardWords[(int)(Math.random()*hardWords.length)];
            placeBlanks(phrase);
            newGame();
            myCardLayout.show(getContentPane(), "GameCard");
            
        //GAMEPLAY BUTTONS
        }else if(source == abcButtons[0][0]){   //A
            abcButtons[0][0].setEnabled(false);     //Disables letter if pressed
            if(checkGuess('a')){                    //If guess is correct     
                                                    //see METHODS above**
            }else{                                  //If guess is incorrect 
                wrongCount++;                       //increment wrongCount
                drawHangman(wrongCount);            //draw appropriate body part
            }
        }else if(source == abcButtons[0][1]){   //B
            abcButtons[0][1].setEnabled(false);
            if(checkGuess('b')){
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
            
        }else if(source == abcButtons[0][2]){   //C
            abcButtons[0][2].setEnabled(false);
            if(checkGuess('c')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }
        else if(source == abcButtons[0][3]){   //D
            abcButtons[0][3].setEnabled(false);
            if(checkGuess('d')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[0][4]){   //E
            abcButtons[0][4].setEnabled(false);
            if(checkGuess('e')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[0][5]){   //F
            abcButtons[0][5].setEnabled(false);
            if(checkGuess('f')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[0][6]){   //G
            abcButtons[0][6].setEnabled(false);
            if(checkGuess('g')){ 
               
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[1][0]){   //H
            abcButtons[1][0].setEnabled(false);
            if(checkGuess('h')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[1][1]){   //I
            abcButtons[1][1].setEnabled(false);
            if(checkGuess('i')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[1][2]){   //J
            abcButtons[1][2].setEnabled(false);
            if(checkGuess('j')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[1][3]){   //K
            abcButtons[1][3].setEnabled(false);
            if(checkGuess('k')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[1][4]){   //L
            abcButtons[1][4].setEnabled(false);
            if(checkGuess('l')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[1][5]){   //M
            abcButtons[1][5].setEnabled(false);
            if(checkGuess('m')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[1][6]){   //N
            abcButtons[1][6].setEnabled(false);
            if(checkGuess('n')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[2][0]){   //O
            abcButtons[2][0].setEnabled(false);
            if(checkGuess('o')){ 
               
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[2][1]){   //P
            abcButtons[2][1].setEnabled(false);
            if(checkGuess('p')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[2][2]){   //Q
            abcButtons[2][2].setEnabled(false);
            if(checkGuess('q')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[2][3]){   //R
            abcButtons[2][3].setEnabled(false);
            if(checkGuess('r')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[2][4]){   //S
            abcButtons[2][4].setEnabled(false);
            if(checkGuess('s')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[2][5]){   //T
            abcButtons[2][5].setEnabled(false);
            if(checkGuess('t')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[2][6]){   //U
            abcButtons[2][6].setEnabled(false);
            if(checkGuess('u')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[3][0]){   //V
            abcButtons[3][0].setEnabled(false);
            if(checkGuess('v')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[3][1]){   //W
            abcButtons[3][1].setEnabled(false);
            if(checkGuess('w')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[3][2]){   //X
            abcButtons[3][2].setEnabled(false);
            if(checkGuess('x')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[3][3]){   //Y
            abcButtons[3][3].setEnabled(false);
            if(checkGuess('y')){ 
                
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[3][4]){   //Z
            abcButtons[3][4].setEnabled(false);
            if(checkGuess('z')){ 
               
            }else{
                wrongCount++;
                drawHangman(wrongCount);
            }
        }else if(source == abcButtons[3][5]){   //Home Button
            myCardLayout.show(getContentPane(), "HomeCard");    //returns
                                                                //to Home Page
        }else if(source == abcButtons[3][6]){   //Quit Button
            super.dispose();                    //closes programs
        }
      
        //After each guess/button click,
        //Check to see if user WON
        if(rightCount == phrase.length()){
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < 7; j++){
                    abcButtons[i][j].setEnabled(false);
                }
            }
            abcButtons[3][5].setEnabled(true);
            abcButtons[3][6].setEnabled(true);
            
            guessThis.setForeground(Color.GREEN.brighter());
        }       
        
        //Also check to see if user LOST
        if(youLose()){
            //disable all buttons...
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < 7; j++){
                    abcButtons[i][j].setEnabled(false);
                }
            }
            //except for HOME and QUIT buttons
            abcButtons[3][5].setEnabled(true);
            abcButtons[3][6].setEnabled(true);
            
            guessThis.setText(phrase);      //Reveals WORD and writes it in RED
            guessThis.setForeground(Color.RED.brighter());
        }
    }
    //-------------------END OF ACTIONPERFORMED METHOD--------------------------
}
