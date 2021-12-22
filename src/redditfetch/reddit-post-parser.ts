import { injectable } from "inversify";
@injectable()
export class RedditPostParser {
  parseBody(postBody: Object): Promise<Object> {
    return new Promise<Object>((resolve, reject) => {
      try {
        let author: String = postBody["author"];
        let subreddit: String = postBody["subreddit"];
        let title: String = postBody["title"];
        let url: String = postBody["url"];
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
        resolve({
          author: author,
          subreddit: subreddit,
          title: title,
          url: url,
        });
      } catch (exception) {
        reject(exception);
      }
    });
  }
}
