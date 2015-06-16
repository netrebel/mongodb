package course;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogPostDAO {
    MongoCollection<Document> postsCollection;

    public BlogPostDAO(final MongoDatabase blogDatabase) {
        postsCollection = blogDatabase.getCollection("posts");
    }

    // Return a single post corresponding to a permalink
    public Document findByPermalink(String permalink) {
        Document query = new Document();
        query.append("permalink", permalink);
        return postsCollection.find(query).first();
    }

    // Return a list of posts in descending order. Limit determines
    // how many posts are returned.
    public List<Document> findByDateDescending(int limit) {

        // Return a list of DBObjects, each one a post from the posts collection
        final List<Document> posts = new ArrayList<>();

        FindIterable<Document> results = postsCollection.find()
                .sort(Sorts.descending("date"))
                .limit(limit);

        results.forEach((Block<Document>) posts::add);
        return posts;
    }


    public String addPost(String title, String body, List tags, String username) {

        System.out.println("inserting blog entry " + title + " " + body);

        String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();

        // Build the post object and insert it
        Document post = new Document();
        post.append("author", username)
                .append("title", title)
                .append("body", body)
                .append("permalink", permalink)
                .append("tags", tags)
                .append("comments", new ArrayList<>())
                .append("date", new Date())
        ;
        postsCollection.insertOne(post);

        return permalink;
    }


    // White space to protect the innocent


    // Append a comment to a blog post
    public void addPostComment(final String name, final String email, final String body,
                               final String permalink) {

        Document comment = new Document();
        comment.append("author", name)
                .append("body", body);

        if (email != null && !email.isEmpty()) {
            comment.append("email", email);
        }

        Document blogPost = findByPermalink(permalink);
        Document query = new Document("_id", blogPost.getObjectId("_id"));
        Document update = new Document();
        update.append("$push", new Document("comments", comment));
        postsCollection.updateOne(query, update);
    }
}
