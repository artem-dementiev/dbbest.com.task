package entities;

/**
 * Interface which using for writing to console or file
 * @author Artem Dementiev
 */
public interface OutputFormat {
    String toConsole();
    String toFile();
}
