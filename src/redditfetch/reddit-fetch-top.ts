import axios from "axios";
import {injectable} from "inversify";
@injectable()
export class RedditFetchTop
{
    fetchTopDailyPosts(subreddit: string, token: string): Promise<Object>{
        return axios.get(
            `https://www.reddit.com/r/${subreddit}/top.json?t=daily`,
            {
                headers: {
                    Authorization: `Basic ${token}`
                }
            }
        ).then((r) => r.data)
    }
}