import requests
from bs4 import BeautifulSoup
import time
import smtplib

class Currency:
    DOLLAR_GRN = 'https://www.google.com/search?channel=trow5&client=firefox-b-d&q=%D0%BA%D1%83%D1%80%D1%81+%D0%B4%D0%BE%D0%BB%D0%BB%D0%B0%D1%80%D0%B0+%D0%BA+%D0%B3%D1%80%D0%B8%D0%B2%D0%BD%D0%B5'
    headers = {
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36'
    }
    current_converted_price = 0
    difference = 5
    currency = 0

    def __init__(self):
        self.current_converted_price = float(self.get_currency_price().replace(",", "."))

    def get_currency_price(self):
        page = requests.get(self.DOLLAR_GRN, headers=self.headers)
        soup = BeautifulSoup(page.content, "lxml")
        convert = soup.findAll("span", {"class": "DFlfde", "class": "SwHCTb", "data-precision": 2})
        return convert[0].text

    def check_currency(self):
        currency = float(self.get_currency_price().replace(",", "."))
        self.currency = currency
        if currency >= self.current_converted_price + self.difference:
            print("!!!!Курс значно збільшився, пора щось робити!!!!")
        elif currency <= self.current_converted_price - self.difference:
            print("!!!!Курс значно зменшився, пора щось робити!!!!")
        else:
            None
        print(f"Курс доллара:{str(currency)}")

    def send_mail(self):
        server = smtplib.SMTP('imap.gmail.com', 587)
        server.ehlo()
        server.starttls()
        server.ehlo()

        server.login('yaroslav2002vas@gmail.com', 'yar075off')

        subject = 'Currency mail'
        body = 'Currency has been changed!'
        message = f'Subject: {subject}\n{body}\nNow dollar is {self.currency} grn.'
        server.sendmail(
            'yaroslav2002vas@gmail.com',
            'kiberchech123@gmail.com',
            message
        )
        server.quit()

    # Создание объекта и вызов метода
currency = Currency()
currency.check_currency()
currency.send_mail()
