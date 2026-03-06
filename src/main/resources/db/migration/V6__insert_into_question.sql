INSERT INTO question (question_name, topic_id, doc_adddate, doc_moddate)
VALUES
    ('Что такое git ?',
     (SELECT topic_id FROM topic WHERE topic_name = 'Git'),
     CURRENT_TIMESTAMP,
     CURRENT_TIMESTAMP),
    ('Какие основные команды Git вам известны ?',
     (SELECT topic_id FROM topic WHERE topic_name = 'Git'),
     CURRENT_TIMESTAMP,
     CURRENT_TIMESTAMP);