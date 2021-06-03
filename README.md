# Bibliopole

Финальная задача (курсы Advanced Java https://codespace.com.ua/)

Инструменты : Maven/ Spring/ JPA(Hibernate)/ REST(Jackson, MapStruct)/ JUnit + plugins.

Entity:
**Book** (Название, Автор, Издательство, Год издания, Количество
страниц, Стоимость)

Функционал :
REST-Controller со след. операциями:
<table>
    <tr>
        <td>Метод</td>
        <td>Путь</td>
        <td>Пример</td>
        <td>Функционал</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/books</td>
        <td>/books</td>
        <td>Полный список книг</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/books/{id}</td>
        <td>/books/100003</td>
        <td>Возвращает книгу по id</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/books</td>
        <td>/books</td>
        <td>Создание книги</td>
    </tr>
    <tr>
        <td>DELETE</td>
        <td>/books/{id}</td>
        <td>/books/100002</td>
        <td>Удаление книги с заданным id</td>
    </tr>
    <tr>
        <td>PATCH</td>
        <td>/books/{id}</td>
        <td>/books/100005</td>
        <td>Редактирование книги с заданным id</td>
    </tr>
    <tr>
        <td>PATCH</td>
        <td>/books/new_cost/{id}?percent=</td>
        <td>/books/new_cost/100005/?percent=10</td>
        <td>Изменение стоимости книги на указанный процент</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/books/filter?name=&author=&publishing&year=</td>
        <td>/books/filter?name=кни&year=1996</td>
        <td>Фильтр (по примеру будут отбраны книги, наименования которых содержат подстроку 'кни' без учета регистра c годом издания от 1996)</td>
    </tr>
</table>

