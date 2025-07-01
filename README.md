"# Document Summarizer using Java & JavaFX" 

A beginner-friendly Java application to intelligently summarize `.txt` documents using extractive summarization. Built using **JavaFX** for a modern user interface, this project helps users identify the **most meaningful lines** in any text file with just a click. 

# Key Features:
- Clean, spacious UI using JavaFX 
- Light/Dark mode
- File selection via native file chooser
- Extractive summarization based on word frequency scoring
- Handles structured content like roadmaps, notes, bullet points, and instructions
- JavaFX modular setup with adjustable layout (resizable)

# How It Works
1. Loads a `.txt` file from your system
2. Reads its contents line-by-line using `Files.readAllLines()`
3. Splits lines and tokenizes each word, calculating **word frequency**
4. Scores each line based on the **average weight** of significant words
5. Sorts and returns the **top 5 highest scoring lines** as the summary
6. Displays the result in a styled text area within the GUI


- **Java 17+**
- **JavaFX SDK 24.0.1**
- **Maven/Gradle (optional for future builds)**
- Standard Java libraries (`java.io`, `java.util`, `javafx.scene.*`)

  # How to Run?

  Prerequisites:
- Java 17 or later
- JavaFX SDK 24.0.1 downloaded and extracted in `/lib`

  Compile

bash
javac --module-path lib/javafx-sdk-24.0.1/lib --add-modules javafx.controls -d out src/*.java

# Run 
java --module-path lib/javafx-sdk-24.0.1/lib --add-modules javafx.controls -cp out Main

# Future Enhancements
- .docx and .pdf summarization using Apache POI / PDFBox
- Export summary to file
- GPT-based summarization via OpenAI API
- Highlighting most frequent keywords
