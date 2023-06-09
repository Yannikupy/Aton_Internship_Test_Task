# In memory simple DB (Тестовое в команду корпоративной шины данных и микросервисов)

# Задание выполнено на Java 17, тесты написаны с применением JUnit 4.

## Используемые алгоритмы и структуры данных

### Для решения поставленной задачи мной был выбран вариант хранения данных в трех ArrayList, которые я поддерживал отсортированными для осуществления бинарного поиска за O(log n). В каждом ArrayList хранились объекты, отсортированные по конкретному полю, т.е один лист для хранения по account, другой - по name, третий - по value.

## Мои идеи и решение

### В ходе размышлений над заданием, я принял решение сделать поле account некоторым уникальным id(дальше так и буду называть). Таким образом, если пользователь пытается добавить запись с уже занятым id, будет выброшено AccountAlreadyExistsException. Тем временем, другие поля необязательно должны быть уникальными. То есть, при поиске записей по полю name, будет возвращен список со всеми записями, где name соответствует указанному, аналогично для value. Если при поиске, обнаруживается, что аккаунта с такими данными нет, выбрасывается AccountNotFoundException. Также предусмотрены операции удаления записи по id и изменения записи по одному из полей, при этом если запись меняется не по id, то будут изменены все записи, которые удовлетворяют заданному условию, но если id меняется на уже существующий в базе, выбрасывается IdAlreadyTakenException.

## Оценка сложности

### В худшем случае при поиске, если у нас в кэше количество совпадающих значений name или value с искомым значением близко к количеству всех значений, сложность будет приближаться к O(n), но такие ситуации весьма маловероятны в реальных задачах. Вставка происходит за O(n * log n), так как после вставки списки сортируются. 
