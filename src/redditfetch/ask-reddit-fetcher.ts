import { RedditEmbedCreator } from "./reddit-embed-creator";
import { RedditFetchTop } from "./reddit-fetch-top";
import { RedditOAuthGenerator } from "./reddit-ouath-generator";
import { RedditPostParser } from "./reddit-post-parser";
import { RedditRandomPostGenerator } from "./reddit-random-post-generator";
import { inject, injectable } from "inversify";
import { TYPES } from "../types";
import { MessageEmbed } from "discord.js";

@injectable()
export class AskRedditFetcher {
  private redditOAuthGenerator: RedditOAuthGenerator;
  private redditFetchTop: RedditFetchTop;
  private redditRandomPostGenerator: RedditRandomPostGenerator;
  private redditPostParser: RedditPostParser;
  private redditEmbedCreator: RedditEmbedCreator;
  constructor(
    @inject(TYPES.RedditOAuthGenerator)
    redditOAuthGenerator: RedditOAuthGenerator,
    @inject(TYPES.RedditFetchTop) redditFetchTop: RedditFetchTop,
    @inject(TYPES.RedditRandomPostGenerator)
    redditRandomPostGenerator: RedditRandomPostGenerator,
    @inject(TYPES.RedditPostParser) redditPostParser: RedditPostParser,
    @inject(TYPES.RedditEmbedCreator) redditEmbedCreator: RedditEmbedCreator
  ) {
    this.redditOAuthGenerator = redditOAuthGenerator;
    this.redditFetchTop = redditFetchTop;
    this.redditRandomPostGenerator = redditRandomPostGenerator;
    this.redditPostParser = redditPostParser;
    this.redditEmbedCreator = redditEmbedCreator;
  }
  fetchRedditEmbed(): Promise<MessageEmbed> {
    return new Promise<MessageEmbed>((resolve, reject) => {
      this.redditOAuthGenerator
        .generateToken()
        .then((token) =>
          this.redditFetchTop.fetchTopDailyPosts("askreddit", token)
        )
        .then((fetchObj) => {
          const rawPostBody = this.redditRandomPostGenerator.pickPost(fetchObj);
          const parsedPostBody = this.redditPostParser.parseBody(rawPostBody);
          const embed = this.redditEmbedCreator.createEmbed(parsedPostBody);
          resolve(embed);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
