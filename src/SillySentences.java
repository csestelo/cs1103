import java.util.Random;

public class SillySentences {
    static final String SPACE = " ";
    static int MAX_RECURSIVE_DEPTH = 3;
    static int RECURSIVE_ATTEMPT = 0;
    static final String[] conjunction = {"and", "or", "but", "because"};
    static final String[] proper_noun = {"Fred", "Jane", "Richard Nixon", "Miss America"};
    static final String[] common_noun = {"man", "woman", "fish", "elephant", "unicorn"};
    static final String[] determiner = {"a", "the", "every", "some"};
    static final String[] adjective = {"big", "tiny", "pretty", "bald"};
    static final String[] intransitive_verb = {"runs", "jumps", "talks", "sleeps"};
    static final String[] transitive_verb = {"loves", "hates", "sees", "knows", "looks for", "finds"};

    public static void main(String[] args) {
        System.out.println(generateSentence());
    }

    static String generateSentence() {
        String simpleSentence = generateSimpleSentence();

        if (RECURSIVE_ATTEMPT <= MAX_RECURSIVE_DEPTH) {
            RECURSIVE_ATTEMPT++;

            String sentence = simpleSentence + SPACE + pickRandomItem(conjunction) + SPACE + generateSentence();
            return booleanChoice() ? sentence : simpleSentence;
        }
        return simpleSentence;
    }

    static String generateSimpleSentence() {
        return generateNounPhrase() + SPACE + generateVerbPhrase();
    }

    static String generateNounPhrase() {
        return booleanChoice() ?
                pickRandomItem(proper_noun)
                : getDeterminerPlusOptionalAdjectives() + SPACE + getCommonNounPlusOptionalVerbPhrase();
    }

        static String generateVerbPhrase() {
        switch (randomChoice()) {
            case 1:
                return pickRandomItem(intransitive_verb);
            case 2:
                return pickRandomItem(transitive_verb) + SPACE + generateNounPhrase();
            case 3:
                return "is " + pickRandomItem(adjective);
            default:
                return "believes that " + generateSimpleSentence();
        }
    }

    static StringBuilder getAdjectives() {
        StringBuilder adjectives = new StringBuilder(pickRandomItem(adjective));
        while (booleanChoice()) {
            adjectives.append(SPACE).append(pickRandomItem(adjective));
        }
        return adjectives;
    }

    static String getDeterminerPlusOptionalAdjectives() {
        return booleanChoice() ? pickRandomItem(determiner) : pickRandomItem(determiner) + SPACE + getAdjectives();
    }

    static String getCommonNounPlusOptionalVerbPhrase() {
        String optionalVerbPhrase = SPACE + "who " + generateVerbPhrase();
        return booleanChoice() ? pickRandomItem(common_noun) : pickRandomItem(common_noun) + optionalVerbPhrase;
    }

    static boolean booleanChoice() {
        return Math.random() < 0.5;
    }

    static int randomChoice() {
        return new Random().nextInt(3) + 1;
    }

    static String pickRandomItem(String[] array) {
        Random rand = new Random();
        return array[rand.nextInt(array.length)];
    }
}
