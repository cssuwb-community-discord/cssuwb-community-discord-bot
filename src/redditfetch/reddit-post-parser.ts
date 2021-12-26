import { injectable } from "inversify";
@injectable()
export class RedditPostParser {
  parseBody(postBody: Object): Object {
    const author: String = postBody["author"];
    const subreddit: String = postBody["subreddit"];
    const title: String = postBody["title"];
    const url: String = postBody["url"];
    if (author == undefined) {
      throw "author is null";
    }
    if (subreddit == undefined) {
      throw "subreddit is null";
    }
    if (title == undefined) {
      throw "title is null";
    }
    if (url == undefined) {
      throw "url is null";
    }
    return {
      author: author,
      subreddit: subreddit,
      title: title,
      url: url,
    };
  }
}
