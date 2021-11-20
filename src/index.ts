// Recommended way of loading dotenv
import container from "./inversify.config";
import { TYPES } from "./types";
import { Bot } from "./bot";
require("dotenv").config();

// Instantiate bot and attempt to log in via token
const bot = container.get<Bot>(TYPES.Bot);

bot
  .listen()
  .then(() => {
    console.log("Logged in!");
  })
  .catch((error) => {
    console.log("Oh no! ", error);
  });
