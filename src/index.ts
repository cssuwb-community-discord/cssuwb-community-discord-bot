// Recommended way of loading dotenv
import container from "./inversify.config";
import { TYPES } from "./types";
import { Bot } from "./bot";
import { DailyTaskService } from "./services/daily-task-service";
require("dotenv").config();

// Instantiate bot and attempt to log in via token
const bot = container.get<Bot>(TYPES.Bot);
const dailyTaskService = container
  .get<DailyTaskService>(TYPES.DailyTaskService);

bot
  .login()
  .then((loginresponse) => {
    console.log("Logged in");
    dailyTaskService.registerDailyTasks();
    console.log("Daily tasks registered");
    bot.listen();
    console.log("Listening for messages");
  })
  .catch((error) => {
    console.log("Oh no! ", error);
  });
