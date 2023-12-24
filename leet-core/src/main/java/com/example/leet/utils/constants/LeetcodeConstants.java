package com.example.leet.utils.constants;

public class LeetcodeConstants {
    public static final String PROFILE_URL_PREFIX = "https://leetcode.com/%s/";
    public static final String LEETCODE_WEEKLY_CONTEST = "https://leetcode.com/contest/api/rasnking/weekly-contest-342/";
	public static final String LEETCODE_BIWEEKLY_CONTEST = "https://leetcode.com/contest/api/ranking/biweekly-contest-120";
	public static final String QUERY_PAGINATION = "pagination";
	public static final String QUERY_REGION = "region";
	// using string for now, has to be removed in later commits
	public static final String FETCH_USER_ACTIVITY_STRING = "query recentActivity($username: String!, $orderBy: TopicSortingOption, $skip: Int, $first: Int , $limit: Int) {\n  userSolutionTopics(\n    username: $username\n    orderBy: $orderBy\n    skip: $skip\n    first: $first\n  ) {\n    pageInfo {\n      hasNextPage\n    }\n    edges {\n      node {\n        id\n        title\n        url\n        viewCount\n        questionTitle\n        post {\n          creationDate\n          voteCount\n        }\n      }\n    }\n  }\n     userCategoryTopics(\n    username: $username\n    orderBy: $orderBy\n    first: $first\n    skip: $skip\n  ) {\n    pageInfo {\n      hasNextPage\n    }\n    edges {\n      node {\n        id\n        title\n        url\n        viewCount\n        post {\n          creationDate\n          voteCount\n        }\n      }\n    }\n  } recentAcSubmissionList(username: $username, limit: $limit) {\n    id\n    title\n    titleSlug\n    timestamp\n  }\n }";
	public static final String GRAPHQL_URL = "https://leetcode.com/graphql/";
	public static final String NEWEST_TO_OLDEST_SORT_ORDER = "newest_to_oldest";
}
