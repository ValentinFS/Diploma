## Отчет по итогам автоматизированного тестирования
___

### Задача проекта:
Провести тестирование функциональности сервиса aqa-shop.jar для покупки туров по карте или в кредит.

На первом этапе было проведено исследовательское тестирование для ознакомления с проектом.
После написания автотестов было проведено автоматизированное тестирование сервиса, включающее в себя проверку позитивных и негативных сценариев покупки тура, тестирование UI, БД.

Сервис был протестирован в соответствии с планом автоматизации тестирования [Plan.md](https://github.com/ValentinFS/Diploma/blob/master/Plan.md)
Протестирована возможность собственной СУБД сохранять информацию о том, каким способом был совершён платёж и успешно ли он был совершён.

Тестирование проведено для двух БД - MySQL и PostgreSQL.

### Количество тест-кейсов

**Всего:** 42 тест - кейса (4 позитивных, 38 негативных)
- Успешных: 22 тест-кейсов - 52,38% 
- Неуспешных: 20 тест-кейсов - 47,62%

Отчет о тестировании Allure:

![img_1.png](img_1.png)


Отчет о тестировании Gradle:
![img_2.png](img_2.png)

На все выявленные ошибки были заведены репорты в [Issues](https://github.com/ValentinFS/Diploma/issues)

### Общие рекомендации
1. Создать документацию для приложения
2. Исправить ошибки, заведенные в репорты Issues
3. В поле "Номер карты" предусмотреть возможность приобретения тура не только 16-ти значной картой. Длина номера карты может быть от 13 до 19 символов.Все вводимые значения в этом промежутке должны быть валидными
4. Проверить настройки валидации полей, включить подходящие под действия подсказки для пользователя (например, если поле не заполнено - сообщение "Поле обязательно для заполнения")
5. Проверить работу БД, выявленные ошибки в которой, в будущем могут привести к критическим дефектам.



