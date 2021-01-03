INSERT INTO students VALUES("18372026", "邝倩茵");
INSERT INTO accounts VALUES("Legion", "123456", "18372026", FALSE);
INSERT INTO students VALUES ("18372000", "小明");
INSERT INTO accounts VALUES ("XiaoMing", "123456", "18372000", FALSE);

INSERT INTO teachers VALUES("A001", "王老师");
INSERT INTO accounts VALUES("Lucky", "123456", "A001", TRUE);
INSERT INTO teachers VALUES("A002", "徐老师");
INSERT INTO accounts VALUES("XuJian", "123456", "A002", TRUE);
INSERT INTO teachers VALUES("A003", "朱老师");
INSERT INTO accounts VALUES("ZhuHou", "123456", "A003", TRUE);

INSERT INTO `subject` VALUES("S001", "JAVA高级编程", "A001");
INSERT INTO `subject` VALUES("S002", "数据结构", "A002");
INSERT INTO `subject` VALUES("S003", "运筹学", "A003");

INSERT INTO subject_signup (`subjectId`, `studentId`) VALUES("S001", "18372026");
INSERT INTO subject_signup (`subjectId`, `studentId`) VALUES("S002", "18372026");

INSERT INTO paper (`subjectId`, `name`, `describe`, `deadline`, `teacherId`) VALUES
	("S001", "第一次作业", "阶段性作业", "2021-02-01", "A001"),
	("S002", "第一次作业", "本次作业要求完成链表相关的题目", "2020-10-01", "A002"),
	("S002", "第二次作业", "本次作业要求完成排序相关的题目", "2021-02-03", "A002");

INSERT INTO question (`title`, `content`) VALUES
	("JavaFX作业", "使用JavaFX的知识编写一个视频"),
	("返回倒数第k个节点", "实现一种算法，找出单向链表中倒数第 k 个节点。返回该节点的值。"),
	("删除节点", "实现一种算法，删除单向链表中间的某个节点（即不是第一个或最后一个节点），假定你只能访问该节点。"),
	("字母异位词", "给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。\n示例 1:\n输入: s = \"anagram\", t = \"nagaram\"\n输出: true\n示例 2:\n输入: s = \"rat\", t = \"car\"\n输出: false\n说明:\n你可以假设字符串只包含小写字母。"),
	("找不同", "给定两个字符串 s 和 t，它们只包含小写字母。\n字符串 t 由字符串 s 随机重排，然后在随机位置添加一个字母。\n请找出在 t 中被添加的字母。");

INSERT INTO paper_question (`paperId`, `questionId`, `fullScore`) VALUES
	(1, 1, 100),
	(2, 2, 50),
	(2, 3, 50),
	(3, 4, 50),
	(3, 5, 50);

INSERT INTO student_answer (`paperId`, `questionId`, `studentId`, `answer`) VALUES
	(2, 2, "18372026", "这是一条答案的测试数据"),
	(2, 3, "18372026", "这也是一条答案的测试数据");