INSERT INTO question (question_name, topic_id, doc_adddate, doc_moddate)
VALUES
    ('Что такое ООП?',
     (SELECT topic_id FROM topic WHERE topic_name = 'ООП'),
     CURRENT_TIMESTAMP,
     CURRENT_TIMESTAMP),
    ('Назовите основные принципы ООП.',
     (SELECT topic_id FROM topic WHERE topic_name = 'ООП'),
     CURRENT_TIMESTAMP,
     CURRENT_TIMESTAMP);