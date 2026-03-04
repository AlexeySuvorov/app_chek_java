insert into topic (topic_name, category_id, doc_adddate, doc_moddate)
values(
          'Что такое git ?',
          (select min(category_id) from category),
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      );