import { injectable } from "inversify";
@injectable()
export class RedditRandomPostGenerator {
  pickPost(responseBody: Object): Promise<Object> {
    return new Promise<Object>((resolve, reject) => {
      try {
        let posts = responseBody["data"]["children"];
        let postCount: number = posts.length;
        let randomIndex = Math.floor(Math.random() * postCount);
        let post: Object = posts[randomIndex]["data"];
        if (post == undefined) {
          throw "post could not be parsed";
        }
        resolve(post);
      } catch (exception) {
        reject(exception);
      }
    });
  }
}
