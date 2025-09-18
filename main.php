<?php
// file: blog.php

$posts = [
  1 => [
    'title' => 'First Post',
    'author' => 'Admin',
    'content' => '<p>This is the <b>first</b> blog post.</p>'
  ],
  2 => [
    'title' => 'Second Post',
    'author' => 'Guest',
    'content' => '<script>alert("Hacked!")</script>This is the second post.'
  ]
];

// ❌ Bug: No input validation or sanitization
$postId = $_GET['id']; // Could be missing or non-numeric

// ❌ Bug: No check for existence of ID in array
$post = $posts[$postId]; 

?>
<!DOCTYPE html>
<html>
<head>
  <title>My Blog</title>
</head>
<body>

<h1><?php echo $post['title'] ?></h1>
<p><em>By <?php echo $post['author']; ?></em></p>

<!-- ❌ Bug: Not escaping content, allows XSS -->
<div>
  <?php echo $post['content']; ?>
</div>

<!-- ❌ Bug: Logic error, 'Back' link is broken -->
<a href="index.phpp">← Back to Blog List</a>

<!-- ❌ Bug: Undefined function -->
<?php displayComments(); ?>

</body>
</html>
