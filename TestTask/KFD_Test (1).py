import numpy as np


#входные данные
user_balance_rub = 1000000
user_balance_usd = 0
user_balance_eur = 0
user_balance_usdt = 0
user_balance_btc = 0
bank_balance_rub = 10000 
bank_balance_usd = 1000
bank_balance_eur = 1000
bank_balance_usdt = 1000
bank_balance_btc = 1000
exchange_rub = 10
exchange_usd = 960
exchange_eur = 1056
exchange_usdt = 960
exchange_btc = 55680000 #стоимость валюты в пунктах, после завершения сделок меняется стоимость каждой валюты


def random_number_generator():
    return 1+np.random.uniform(-5,5)/100 #возвращает кеф изменения курса от 0.95 до 1.05



def main_menu():
    print("-----------------")
    print("Добро пожаловать в обменник Fraudbit!")
    print("Ваш баланс:")
    print(user_balance_rub, "RUB")
    print(user_balance_usd, "USD")
    print(user_balance_eur, "EUR")
    print(user_balance_usdt, "USDT")
    print(user_balance_btc, "BTC")
    while True:
        print('------------------')
        print('Пожалуйста, выберите валютную пару: 1 - RUB/USD, 2 - RUB/EUR , 3 - USD/EUR, 4 - USD/USDT, 5 - USD/BTC')
        print('Для выхода напишите 0, чтобы узнать ваш баланс напишите bal')
        entered_value = input()
        if entered_value == '0':
            print("Благодарим за использование нашего обменника!")
            break
        elif entered_value == '1':
            rub_usd_actions()
        elif entered_value == '2':
            rub_eur_actions()
        elif entered_value == '3':
            usd_eur_actions()
        elif entered_value == '4':
            usd_usdt_actions()
        elif entered_value == '5':
            usd_btc_actions()
        elif entered_value == 'bal':
            print("Ваш баланс:")
            print(user_balance_rub, "RUB")
            print(user_balance_usd, "USD")
            print(user_balance_eur, "EUR")
            print(user_balance_usdt, "USDT")
            print(user_balance_btc, "BTC")
        else:
            print('Вы ввели неправильное значение. Введите цифру от 0 до 5. Попробуйте еще раз.')


def rub_usd_actions():
    global exchange_usd, exchange_rub, user_balance_rub, user_balance_usd, bank_balance_rub, bank_balance_usd
    print("Текущий курс: 1 USD =", "{:.2f}".format(exchange_usd / exchange_rub), "RUB")
    print("У вас на счету", user_balance_rub, "RUB и", user_balance_usd, "USD.")
    print("Доступны для покупки", bank_balance_rub, "RUB  и", bank_balance_usd, "USD.")
    trade_happened_flag = False
    while True:    
        entered_value = input('Выберите валюту для покупки (1 - RUB, 2 - USD, 0 - вернуться в главное меню): ')
        if entered_value == '0':
            break
        elif entered_value == '1':
            print("У вас в наличии", user_balance_usd, "USD.")
            possible_rub_sum = min((exchange_usd / exchange_rub) * user_balance_usd, bank_balance_rub)
            print("Вы можете купить", possible_rub_sum, "RUB.")
            print("Введите количество RUB, которое вы желаете приобрести за USD (введите 0 для отмены сделки).")
            while True:
                entered_sum = input()
                if entered_sum.isdigit() or (entered_sum[0] == '-' and entered_sum[1:].isdigit()):
                    entered_sum = int(entered_sum)
                    if entered_sum < 0:
                        print("Вы ввели отрицательное число. Попробуйте снова.")
                    elif entered_sum == 0:
                        break
                    elif entered_sum > possible_rub_sum:
                        print("Вы не можете приобрести столько RUB. Попробуйте снова.")
                    else:
                        user_balance_rub += entered_sum
                        bank_balance_rub -= entered_sum
                        user_balance_usd -= entered_sum * exchange_rub / exchange_usd
                        bank_balance_usd += entered_sum * exchange_rub / exchange_usd
                        trade_happened_flag = True
                        print("Успешная покупка. Курс валюты обновлён.")
                        break   
                else:
                    print("Вы ввели не число. Попробуйте снова.")
        elif entered_value == '2':
            print("У вас в наличии", user_balance_rub, "RUB.")
            possible_usd_sum = min((exchange_rub / exchange_usd) * user_balance_rub, bank_balance_usd)
            print("Вы можете купить", possible_usd_sum, "USD.")
            print("Введите количество USD, которое вы желаете приобрести за RUB (введите 0 для отмены сделки).")
            while True:
                entered_sum = input()
                if entered_sum.isdigit() or (entered_sum[0] == '-' and entered_sum[1:].isdigit()):
                    entered_sum = int(entered_sum)
                    if entered_sum < 0:
                        print("Вы ввели отрицательное число. Попробуйте снова.")
                    elif entered_sum == 0:
                        break
                    elif entered_sum > possible_usd_sum:
                        print("Вы не можете приобрести столько USD. Попробуйте снова.")
                    else:
                        user_balance_usd += entered_sum
                        bank_balance_usd -= entered_sum
                        user_balance_rub -= entered_sum * exchange_usd / exchange_rub
                        bank_balance_rub += entered_sum * exchange_usd / exchange_rub
                        trade_happened_flag = True
                        print("Успешная покупка. Курс валюты обновлён.")
                        break   
                else:
                    print("Вы ввели не число. Попробуйте снова.")
        else:
            print('Попробуйте ещё раз. Вводите только 0, 1 или 2.')
        if trade_happened_flag:
            break
    if trade_happened_flag:
        exchange_usd *= random_number_generator()
        exchange_rub *= random_number_generator()
    return



def rub_eur_actions():
    global exchange_rub, exchange_eur, user_balance_rub, user_balance_eur, bank_balance_rub, bank_balance_eur
    print("Текущий курс: 1 EUR =", "{:.2f}".format(exchange_eur / exchange_rub), "RUB")
    print("У вас на счету", user_balance_rub, "RUB и", user_balance_eur, "EUR.")
    print("Доступны для покупки", bank_balance_rub, "RUB  и", bank_balance_eur, "EUR.")
    trade_happened_flag = False
    while True:    
        entered_value = input('Выберите валюту для покупки (1 - RUB, 2 - EUR, 0 - вернуться в главное меню): ')
        if entered_value == '0':
            break
        elif entered_value == '1':
            print("У вас в наличии", user_balance_eur, "EUR.")
            possible_rub_sum = min((exchange_eur / exchange_rub) * user_balance_eur, bank_balance_rub)
            print("Вы можете купить", possible_rub_sum, "RUB.")
            print("Введите количество RUB, которое вы желаете приобрести за EUR (введите 0 для отмены сделки).")
            while True:
                entered_sum = input()
                if entered_sum.isdigit() or (entered_sum[0] == '-' and entered_sum[1:].isdigit()):
                    entered_sum = int(entered_sum)
                    if entered_sum < 0:
                        print("Вы ввели отрицательное число. Попробуйте снова.")
                    elif entered_sum == 0:
                        break
                    elif entered_sum > possible_rub_sum:
                        print("Вы не можете приобрести столько RUB. Попробуйте снова.")
                    else:
                        user_balance_rub += entered_sum
                        bank_balance_rub -= entered_sum
                        user_balance_eur -= entered_sum * exchange_rub / exchange_eur
                        bank_balance_eur += entered_sum * exchange_rub / exchange_eur
                        trade_happened_flag = True
                        print("Успешная покупка. Курс валюты обновлён.")
                        break   
                else:
                    print("Вы ввели не число. Попробуйте снова.")
        elif entered_value == '2':
            print("У вас в наличии", user_balance_rub, "RUB.")
            possible_eur_sum = min((exchange_rub / exchange_eur) * user_balance_rub, bank_balance_eur)
            print("Вы можете купить", possible_eur_sum, "EUR.")
            print("Введите количество EUR, которое вы желаете приобрести за RUB (введите 0 для отмены сделки).")
            while True:
                entered_sum = input()
                if entered_sum.isdigit() or (entered_sum[0] == '-' and entered_sum[1:].isdigit()):
                    entered_sum = int(entered_sum)
                    if entered_sum < 0:
                        print("Вы ввели отрицательное число. Попробуйте снова.")
                    elif entered_sum == 0:
                        break
                    elif entered_sum > possible_eur_sum:
                        print("Вы не можете приобрести столько EUR. Попробуйте снова.")
                    else:
                        user_balance_eur += entered_sum
                        bank_balance_eur -= entered_sum
                        user_balance_rub -= entered_sum * exchange_eur / exchange_rub
                        bank_balance_rub += entered_sum * exchange_eur / exchange_rub
                        trade_happened_flag = True
                        print("Успешная покупка. Курс валюты обновлён.")
                        break   
                else:
                    print("Вы ввели не число. Попробуйте снова.")
        else:
            print('Попробуйте ещё раз. Вводите только 0, 1 или 2.')
        if trade_happened_flag:
            break
    if trade_happened_flag:
        exchange_eur *= random_number_generator()
        exchange_rub *= random_number_generator()
    return
        


def usd_eur_actions():
    global exchange_usd, exchange_eur, user_balance_eur, user_balance_usd, bank_balance_eur, bank_balance_usd
    print("Текущий курс: 1 EUR =", "{:.2f}".format(exchange_eur / exchange_usd), "USD")
    print("У вас на счету", user_balance_eur, "EUR и", user_balance_usd, "USD.")
    print("Доступны для покупки", bank_balance_eur, "EUR  и", bank_balance_usd, "USD.")
    trade_happened_flag = False
    while True:    
        entered_value = input('Выберите валюту для покупки (1 - EUR, 2 - USD, 0 - вернуться в главное меню): ')
        if entered_value == '0':
            break
        elif entered_value == '1':
            print("У вас в наличии", user_balance_usd, "USD.")
            possible_eur_sum = min((exchange_usd / exchange_eur) * user_balance_usd, bank_balance_eur)
            print("Вы можете купить", possible_eur_sum, "EUR.")
            print("Введите количество EUR, которое вы желаете приобрести за USD (введите 0 для отмены сделки).")
            while True:
                entered_sum = input()
                if entered_sum.isdigit() or (entered_sum[0] == '-' and entered_sum[1:].isdigit()):
                    entered_sum = int(entered_sum)
                    if entered_sum < 0:
                        print("Вы ввели отрицательное число. Попробуйте снова.")
                    elif entered_sum == 0:
                        break
                    elif entered_sum > possible_eur_sum:
                        print("Вы не можете приобрести столько EUR. Попробуйте снова.")
                    else:
                        user_balance_eur += entered_sum
                        bank_balance_eur -= entered_sum
                        user_balance_usd -= entered_sum * exchange_eur / exchange_usd
                        bank_balance_usd += entered_sum * exchange_eur / exchange_usd
                        trade_happened_flag = True
                        print("Успешная покупка. Курс валюты обновлён.")
                        break   
                else:
                    print("Вы ввели не число. Попробуйте снова.")
        elif entered_value == '2':
            print("У вас в наличии", user_balance_eur, "EUR.")
            possible_usd_sum = min((exchange_eur / exchange_usd) * user_balance_eur, bank_balance_usd)
            print("Вы можете купить", possible_usd_sum, "USD.")
            print("Введите количество USD, которое вы желаете приобрести за RUB (введите 0 для отмены сделки).")
            while True:
                entered_sum = input()
                if entered_sum.isdigit() or (entered_sum[0] == '-' and entered_sum[1:].isdigit()):
                    entered_sum = int(entered_sum)
                    if entered_sum < 0:
                        print("Вы ввели отрицательное число. Попробуйте снова.")
                    elif entered_sum == 0:
                        break
                    elif entered_sum > possible_usd_sum:
                        print("Вы не можете приобрести столько USD. Попробуйте снова.")
                    else:
                        user_balance_usd += entered_sum
                        bank_balance_usd -= entered_sum
                        user_balance_eur -= entered_sum * exchange_usd / exchange_eur
                        bank_balance_eur += entered_sum * exchange_usd / exchange_eur
                        trade_happened_flag = True
                        print("Успешная покупка. Курс валюты обновлён.")
                        break   
                else:
                    print("Вы ввели не число. Попробуйте снова.")
        else:
            print('Попробуйте ещё раз. Вводите только 0, 1 или 2.')
        if trade_happened_flag:
            break
    if trade_happened_flag:
        exchange_usd *= random_number_generator()
        exchange_eur *= random_number_generator()
    return


def usd_usdt_actions():
    global exchange_usd, exchange_usdt, user_balance_usdt, user_balance_usd, bank_balance_usdt, bank_balance_usd
    print("Текущий курс: 1 USDT =", "{:.2f}".format(exchange_usdt / exchange_usd), "USD")
    print("У вас на счету", user_balance_usdt, "USDT и", user_balance_usd, "USD.")
    print("Доступны для покупки", bank_balance_usdt, "USDT  и", bank_balance_usd, "USD.")
    trade_happened_flag = False
    while True:    
        entered_value = input('Выберите валюту для покупки (1 - USDT, 2 - USD, 0 - вернуться в главное меню): ')
        if entered_value == '0':
            break
        elif entered_value == '1':
            print("У вас в наличии", user_balance_usd, "USD.")
            possible_usdt_sum = min((exchange_usd / exchange_usdt) * user_balance_usd, bank_balance_usdt)
            print("Вы можете купить", possible_usdt_sum, "USDT.")
            print("Введите количество USDT, которое вы желаете приобрести за USD (введите 0 для отмены сделки).")
            while True:
                entered_sum = input()
                if entered_sum.isdigit() or (entered_sum[0] == '-' and entered_sum[1:].isdigit()):
                    entered_sum = int(entered_sum)
                    if entered_sum < 0:
                        print("Вы ввели отрицательное число. Попробуйте снова.")
                    elif entered_sum == 0:
                        break
                    elif entered_sum > possible_usdt_sum:
                        print("Вы не можете приобрести столько USDT. Попробуйте снова.")
                    else:
                        user_balance_usdt += entered_sum
                        bank_balance_usdt -= entered_sum
                        user_balance_usd -= entered_sum * exchange_usdt / exchange_usd
                        bank_balance_usd += entered_sum * exchange_usdt / exchange_usd
                        trade_happened_flag = True
                        print("Успешная покупка. Курс валюты обновлён.")
                        break   
                else:
                    print("Вы ввели не число. Попробуйте снова.")
        elif entered_value == '2':
            print("У вас в наличии", user_balance_usdt, "USDT.")
            possible_usd_sum = min((exchange_usdt / exchange_usd) * user_balance_usdt, bank_balance_usd)
            print("Вы можете купить", possible_usd_sum, "USD.")
            print("Введите количество USD, которое вы желаете приобрести за USDT (введите 0 для отмены сделки).")
            while True:
                entered_sum = input()
                if entered_sum.isdigit() or (entered_sum[0] == '-' and entered_sum[1:].isdigit()):
                    entered_sum = int(entered_sum)
                    if entered_sum < 0:
                        print("Вы ввели отрицательное число. Попробуйте снова.")
                    elif entered_sum == 0:
                        break
                    elif entered_sum > possible_usd_sum:
                        print("Вы не можете приобрести столько USD. Попробуйте снова.")
                    else:
                        user_balance_usd += entered_sum
                        bank_balance_usd -= entered_sum
                        user_balance_usdt -= entered_sum * exchange_usd / exchange_usdt
                        bank_balance_usdt += entered_sum * exchange_usd / exchange_usdt
                        trade_happened_flag = True
                        print("Успешная покупка. Курс валюты обновлён.")
                        break   
                else:
                    print("Вы ввели не число. Попробуйте снова.")
        else:
            print('Попробуйте ещё раз. Вводите только 0, 1 или 2.')
        if trade_happened_flag:
            break
    if trade_happened_flag:
        exchange_usd *= random_number_generator()
        exchange_usdt *= random_number_generator()
    return



def usd_btc_actions():
    global exchange_usd, exchange_btc, user_balance_btc, user_balance_usd, bank_balance_btc, bank_balance_usd
    print("Текущий курс: 1 BTC =", "{:.2f}".format(exchange_btc / exchange_usd), "USD")
    print("У вас на счету", user_balance_btc, "BTC и", user_balance_usd, "USD.")
    print("Доступны для покупки", bank_balance_btc, "BTC  и", bank_balance_usd, "USD.")
    trade_happened_flag = False
    while True:    
        entered_value = input('Выберите валюту для покупки (1 - BTC, 2 - USD, 0 - вернуться в главное меню): ')
        if entered_value == '0':
            break
        elif entered_value == '1':
            print("У вас в наличии", user_balance_usd, "USD.")
            possible_btc_sum = min((exchange_usd / exchange_btc) * user_balance_usd, bank_balance_btc)
            print("Вы можете купить", possible_btc_sum, "BTC.")
            print("Введите количество BTC, которое вы желаете приобрести за USD (введите 0 для отмены сделки).")
            while True:
                entered_sum = input()
                if entered_sum.isdigit() or (entered_sum[0] == '-' and entered_sum[1:].isdigit()):
                    entered_sum = int(entered_sum)
                    if entered_sum < 0:
                        print("Вы ввели отрицательное число. Попробуйте снова.")
                    elif entered_sum == 0:
                        break
                    elif entered_sum > possible_btc_sum:
                        print("Вы не можете приобрести столько BTC. Попробуйте снова.")
                    else:
                        user_balance_btc += entered_sum
                        bank_balance_btc -= entered_sum
                        user_balance_usd -= entered_sum * exchange_btc / exchange_usd
                        bank_balance_usd += entered_sum * exchange_btc / exchange_usd
                        trade_happened_flag = True
                        print("Успешная покупка. Курс валюты обновлён.")
                        break   
                else:
                    print("Вы ввели не число. Попробуйте снова.")
        elif entered_value == '2':
            print("У вас в наличии", user_balance_btc, "BTC.")
            possible_usd_sum = min((exchange_btc / exchange_usd) * user_balance_btc, bank_balance_usd)
            print("Вы можете купить", possible_usd_sum, "USD.")
            print("Введите количество USD, которое вы желаете приобрести за BTC (введите 0 для отмены сделки).")
            while True:
                entered_sum = input()
                if entered_sum.isdigit() or (entered_sum[0] == '-' and entered_sum[1:].isdigit()):
                    entered_sum = int(entered_sum)
                    if entered_sum < 0:
                        print("Вы ввели отрицательное число. Попробуйте снова.")
                    elif entered_sum == 0:
                        break
                    elif entered_sum > possible_usd_sum:
                        print("Вы не можете приобрести столько USD. Попробуйте снова.")
                    else:
                        user_balance_usd += entered_sum
                        bank_balance_usd -= entered_sum
                        user_balance_btc -= entered_sum * exchange_usd / exchange_btc
                        bank_balance_btc += entered_sum * exchange_usd / exchange_btc
                        trade_happened_flag = True
                        print("Успешная покупка. Курс валюты обновлён.")
                        break   
                else:
                    print("Вы ввели не число. Попробуйте снова.")

        else:
            print('Попробуйте ещё раз. Вводите только 0, 1 или 2.')
        if trade_happened_flag:
            break
    if trade_happened_flag:
        exchange_usd *= random_number_generator()
        exchange_btc *= random_number_generator()
    return


main_menu()