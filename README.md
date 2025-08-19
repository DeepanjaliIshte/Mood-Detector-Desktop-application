# Mood-Detector-Desktop-application


<h1 align="center">🎭 Mood Detector – Desktop App</h1>

<p align="center">
  A colorful <b>Java Swing</b> application that analyzes mood from text using simple NLP (keyword mapping),
  shows a big emoji-based result with confidence, and keeps a local history.
</p>

<p align="center">
  <a href="#-features">Features</a> •
  <a href="#-tech-stack">Tech Stack</a> •
  <a href="#-screenshots">Screenshots</a> •
  <a href="#-quick-start">Quick Start</a> •
  <a href="#-project-structure">Structure</a> •
  <a href="#-usage--samples">Usage</a> •
  <a href="#-roadmap">Roadmap</a> •
  <a href="#-license">License</a>
</p>

<hr/>

<!-- ABOUT THE PROJECT -->
<h2 id="-features">✨ Features</h2>
<ul>
  <li>📝 Type any text and get a detected <b>mood + confidence</b>.</li>
  <li>🎨 <b>Colorful UI</b> with gradient background, big emoji result, and modern feel.</li>
  <li>🧠 Simple, transparent <b>keyword-based analysis</b> (joy, sadness, anger, anxiety, contentment).</li>
  <li>🗂️ <b>Local history</b> saved to <code>mood_history.txt</code> with timestamp.</li>
  <li>🔒 No internet required; runs completely offline.</li>
</ul>

<h2 id="-tech-stack">🧰 Tech Stack</h2>
<ul>
  <li><b>Language:</b> Java 17+</li>
  <li><b>UI:</b> Swing</li>
  <li><b>Build:</b> Maven</li>
  <li><b>Optional Service Layer:</b> Spring @Service (if integrating with web/REST later)</li>
</ul>

<!-- QUICK START -->
<h2 id="-quick-start">⚡ Quick Start</h2>

<h3>Prerequisites</h3>
<ul>
  <li>Java 17+ (<code>java -version</code>)</li>
  <li>Maven 3.8+ (<code>mvn -v</code>)</li>
</ul>

<h3>Clone & Run</h3>

<pre>
git clone https://github.com/YOUR_GITHUB_USERNAME/YOUR_REPO.git
cd YOUR_REPO
mvn clean package
java -jar target/mood-detector-1.0.0.jar
</pre>

<p>
If you run from IDE:
<ol>
  <li>Open project as Maven project.</li>
  <li>Run <code>com.mooddetector.Mood_Detecor.MoodDetectorApp</code> (main class).</li>
</ol>
</p>

<!-- USAGE -->
<h2 id="-usage--samples">🕹️ Usage & Samples</h2>
<ol>
  <li>Open the app, type your text, and click <kbd>Analyze Mood</kbd>.</li>
  <li>See detected mood, confidence %, and emoji.</li>
  <li>Recent results appear in the right-side History panel. Use <kbd>Clear History</kbd> if needed.</li>
</ol>

<h3>Sample Texts (for testing)</h3>
<ul>
  <li><i>Excited:</i> “I’m super thrilled to work on my placement project today!”</li>
  <li><i>Sad:</i> “Feeling low and disappointed about my progress.”</li>
  <li><i>Angry:</i> “This bug is so frustrating and makes me angry.”</li>
  <li><i>Anxious:</i> “I’m nervous and worried about the interview.”</li>
  <li><i>Content:</i> “I feel calm and satisfied with today’s work.”</li>
</ul>

<!-- STRUCTURE -->
<h2 id="-project-structure">🗂️ Project Structure</h2>

<pre>
src/
└── main/
    └── java/
        └── com/mooddetector/Mood_Detecor/
            ├── MoodDetectorApp.java     &lt;-- Swing UI (colorful version)
            ├── MoodAnalyzer.java        &lt;-- Keyword-based mood logic
            ├── MoodResult.java          &lt;-- POJO for result (mood, confidence)
            ├── MoodEntry.java           &lt;-- POJO for history entries
            └── MoodAnalysisService.java &lt;-- Optional Spring @Service (if needed)
</pre>

<!-- HIGHLIGHTS / IMPLEMENTATION -->
<h2>🧩 Implementation Highlights</h2>
<ul>
  <li><b>Emoji mapping</b> for visual feedback (😄 😢 😠 😰 😌 🎉).</li>
  <li><b>Confidence</b> computed from keyword count vs. total tokens.</li>
  <li><b>Persistence:</b> simple file writing to <code>mood_history.txt</code> with ISO timestamp.</li>
  <li><b>UI polish:</b> gradient background, bold headers, styled buttons.</li>
</ul>

<!-- HOW TO BUILD JAR/EXE -->
<h2>📦 Build Notes</h2>
<h3>Jar</h3>
<pre>
mvn clean package
java -jar target/mood-detector-1.0.0.jar
</pre>

<h3>Windows EXE (optional)</h3>
<ul>
  <li>Use tools like <code>launch4j</code> or <code>jpackage</code> to wrap the jar.</li>
  <li>Example (jpackage):</li>
</ul>

<pre>
jpackage --input target ^
         --name "MoodDetector" ^
         --main-jar mood-detector-1.0.0.jar ^
         --type exe
</pre>

<!-- CONTRIBUTING -->
<h2>🤝 Contributing</h2>
<p>Contributions are welcome! Please open an issue or submit a PR for improvements (UI themes, new mood categories, better NLP).</p>

<!-- ROADMAP -->
<h2 id="-roadmap">🗺️ Roadmap</h2>
<ul>
  <li>[ ] Add sound/animation on result</li>
  <li>[ ] Export history to CSV/JSON</li>
  <li>[ ] Multi-language keyword lists</li>
  <li>[ ] Optional REST API using Spring Boot</li>
  <li>[ ] Replace keywords with ML model (e.g., simple TF-IDF or ONNX runtime)</li>
</ul>

<!-- CONTACT -->
<h2>📫 Contact</h2>
<p>
  Developed by <b>Deepanjali</b> – MCA Student<br/>
  

https://github.com/user-attachments/assets/f2f4a46d-ccca-4a1f-bada-d65814a4c027


</p>
