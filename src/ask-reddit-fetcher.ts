import { RedditEmbedCreator } from "./redditfetch/reddit-embed-creator";
import { RedditFetchTop } from "./redditfetch/reddit-fetch-top";
import { RedditOAuthGenerator } from "./redditfetch/reddit-ouath-generator";
import { RedditPostParser } from "./redditfetch/reddit-post-parser";
import { RedditRandomPostGenerator } from "./redditfetch/reddit-random-post-generator";
import { inject, injectable } from "inversify";
import { TYPES } from "./types";
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
      try {
        this.redditOAuthGenerator
          .generateToken()
          .then((token) =>
            this.redditFetchTop.fetchTopDailyPosts("askreddit", token)
          )
          .then((fetchObj) => this.redditRandomPostGenerator.pickPost(fetchObj))
          .then((postObj) => this.redditPostParser.parseBody(postObj))
          .then((parsedPostObj) =>
            this.redditEmbedCreator.createEmbed(parsedPostObj)
          )
          .then((embed) => resolve(embed));
      } catch (exception) {
        reject(exception);
      }
    });
  }
}
