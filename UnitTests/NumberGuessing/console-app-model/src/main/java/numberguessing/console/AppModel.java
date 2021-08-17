package numberguessing.console;

import java.util.stream.Stream;
import numberguessing.PositiveIntegerGenerator;

public final class AppModel {

  private final static String NEW_LINE = System.lineSeparator();
  private static final String SELECT_MODE_MESSAGE =
      "1: Single player game" + NEW_LINE
      + "2: Multiplayer name" + NEW_LINE
      + "3: Exit" + NEW_LINE
      + "Enter selection: ";

  interface Processor {
    Processor run(String input);
  }

  private final PositiveIntegerGenerator generator;
  private boolean completed;
  private StringBuffer outputBuffer;
  private Processor processor;

  public AppModel(PositiveIntegerGenerator generator) {
    this.generator = generator;
    outputBuffer = new StringBuffer(SELECT_MODE_MESSAGE);
    completed = false;
    processor = this::processModeSelection;
  }

  public boolean isCompleted() {
    return completed;
  }

  public String flushOutput() {
    String output = outputBuffer.toString();
    outputBuffer.setLength(0);
    return output;
  }

  public void processInput(String input) {
    processor = processor.run(input);
  }

  private Processor processModeSelection(String input) {
    if (input.equals("1")) {
      outputBuffer.append("Single player game" + NEW_LINE
          + "I'm thinking of a number between 1 and 100." + NEW_LINE
          + "Enter your guess: ");
      int answer = generator.generateLessThanOrEqualToHundred();
      return getSinglePlayerGameProcessor(answer, 1);
    } else if (input.equals("2")) {
      outputBuffer.append("Multiplayer game" + NEW_LINE + "Enter player name separated with comma: ");
      return startMultiPlayerGame();
    } else {
      completed = true;
      return null;
    }
  }

  private Processor startMultiPlayerGame() {
    return input -> {
      Object[] players = Stream.of(input.split(",")).map(i -> i.trim()).toArray();
      outputBuffer.append("I'm thinking of a number between 1 and 100.");
      int answer = generator.generateLessThanOrEqualToHundred();
      return getMultiPlayerGameProcessor(players, answer, 1);
    };
  }

  private Processor getMultiPlayerGameProcessor(Object[] players, int answer, int tries) {
    Object player = players[(tries - 1) % players.length];
    outputBuffer.append("Enter " + player + "'s guess: ");
    return input -> {
      int guess = Integer.parseInt(input);
      if (guess < answer) {
        outputBuffer.append(player + "'s guess is too low." + NEW_LINE);
        return getMultiPlayerGameProcessor(players, answer, tries + 1);
      } else if (guess > answer) {
        outputBuffer.append(player + "'s guess is too high." + NEW_LINE);
        return getMultiPlayerGameProcessor(players, answer, tries + 1);
      } else {
        outputBuffer.append("Correct! ");
        outputBuffer.append(player + " wins." + NEW_LINE);
        outputBuffer.append(SELECT_MODE_MESSAGE);
        return this::processModeSelection;
      }
    };
  }

  private Processor getSinglePlayerGameProcessor(int answer, int tries) {
    return input -> {
      int guess = Integer.parseInt(input);
      if (guess < answer) {
        outputBuffer.append("Your guess is too low." + NEW_LINE + "Enter your guess: ");
        return getSinglePlayerGameProcessor(answer, tries + 1);
      } else if (guess > answer) {
        outputBuffer.append("Your guess is too high." + NEW_LINE + "Enter your guess: ");
        return getSinglePlayerGameProcessor(answer, tries + 1);
      } else {
        outputBuffer.append("Correct! " + tries + (tries == 1 ? " guess." : " guesses.") + NEW_LINE
            + SELECT_MODE_MESSAGE);
        return this::processModeSelection;
      }
    };
  }
}
