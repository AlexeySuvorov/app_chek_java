insert into question (question_name, topic_id, doc_adddate, doc_moddate)
values (
    'Что такое git ?',
    (select topic_id from topic where topic_name = 'Git'),
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);