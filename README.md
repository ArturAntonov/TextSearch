# TextSearch
My Text_Search project for data processing using Java

My first serious app for text searching, fuzzy search.
Моя проба пера в боласти поисковых систем и fuzzy search. Данное приложение работает пока что только в консоли.
В этом приложении  я постарался реализовать полученные мной знания в области поисковых технологий. 
Создание индекса, токенизация, поиск по ключевым запросам, ранжирование, ведение диалога с оператором для более лучших результатов.

Данная система была разработана для реальной обработки данных в нашей команде.
На вход пустали опросники от сотрудников компании, где мы ведем проект. 
Из этих опросников необходимо было получить информацию об установленном программном обеспечении, сверить с нашей базой ивзестного ПО, выделить лица, у которых есть неизвестное ПО.
Движок поисковой системы я делал сам.

В некоторой мере система работает. Производится поиск, учтены "незначащие" слова из опросника, выдается результат поиска довольно таки правильный.
Косяки в том, что сотрудники в опроснике записывают зачастую названия ПО так, как им захочется. Что-то я смог учесть. Что-то нет.

Созданное мной решение не идеальное. Не хватает прямой работы с экселевскими файлами. Сейчас я работаю с .csv файлами как с текстовыми.
На хватает обновления словаря "на лету". Не хватает точности в определении названий.

Однако, если учесть объемы опросников, и время обработки этим скриптом, то даже при таком уровне точности выигрыш по времени обработки очевиден.

В планах изменить это приложение на работу с apache Lucene.
