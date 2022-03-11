# Requirements
Java 17<br>
Gradle version 7.3.3

# Application Setup

## Settings file
To properly setup this bot, you must create the file `settings.json` and store it in the `src/main/resource` folder. The file should follow the format:
```
{
  "discord": {
    "bot_token": string
    "guild_id": string
    "application_id": string
    "daily_time": {
      "hour": int
      "minute": int
    },
    "leetcodeChannel": string
    "redditChannel": string
    "hackerrankChannel": string
  }
}
```
The `dailyTime` object operates in 24 hour time, so the possible hour values are `0-23`, and the possible minute values are `0-59`. The application will also operate in the timezone of the machine it is running on.<br>
Once this file has been created and placed in the directory, the application can be run.

## Building/Running the Application
To build the application, run `gradle build` in the root directory. Extract the jar file from `build/libs/csuwbbot-1.0.0.jar`. Run the jar file using `java -jar csuwbbot-1.0.0.jar` 

# Features
<li>Sends a Leetcode problem every day into the channel of your choice</li>
<li>Sends a post from r/askReddit every day into the channel of your choice</li>
<li>Request a Leetcode problem of any difficulty using a slash command</li>
<li>Request a post from r/askReddit using a slash command</li>