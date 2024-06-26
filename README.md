# EliminationStack

## Характеристики машины
 - Процессор: AMD Ryzen 7 5800H
 - RAM: 16GB
 - OC: Ubuntu 24.04 LTS x86_64

 ## Сравнение времени работы TreiberStack и EliminationStack
 
 Каждый поток выполнял 1_000_000 операций, замер проводился на 1, 2, 4,8 и 16 потоках. Результат - среднее из 10 запусков.

 При случайном выборе операций:

N = 1 | elimTime = 105 ms | treiber = 45 ms

N = 2 | elimTime = 111 ms | treiber = 96 ms

N = 4 | elimTime = 255 ms | treiber = 231 ms

N = 8 | elimTime = 613 ms | treiber = 600 ms

N = 16 | elimTime = 1351 ms | treiber = 1398 ms

При чередовании push и pop:

N = 1 | elimTime = 177 ms | treiber = 165 ms

N = 2 | elimTime = 157 ms | treiber = 343 ms

N = 4 | elimTime = 376 ms | treiber = 692 ms

N = 8 | elimTime = 854 ms | treiber = 1653 ms

N = 16 | elimTime = 1813 ms | treiber = 2992 ms

## Результаты

При случайном наборе операций время выполнения вышло примерно одинаковым, а при чередовании комплементарных операций наблюдаем ускорение в 2 раза. Так же эту разницу можно менять настраивая backoff стратегию, при удачных и неудачных визитах в массив для элиминации, изменять количество попыток для элиминации и диапозон массива, в котором пытаемся совершить обмен.

Так же структуры были протестированы на корректность с помощью lincheck.