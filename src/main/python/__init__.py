import requests
import re
import openpyxl
import logging

logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s: %(message)s')
headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1"
}

wb = openpyxl.Workbook()    # 创建工作簿对象
sheet = wb.active           # 获取活动的工作表
# 编程语言   时间    热度
sheet.append(['Programing', 'Date', 'data_per'])

url = 'https://www.tiobe.com/tiobe-index/'
rep = requests.get(url, headers=headers).text

# 正则匹配提取数据
data = re.findall('{name : (.*?),data : (.*?)}', rep)
programing = [eval(k[0]) for k in data]     # 编程语言
dates = [i[1] for i in data]

# 正则表达式处理 提取出想要的数据
for x in range(len(dates)):
    name = programing[x]
    datas = re.findall(r'\[Date.UTC(.*?)]', dates[x], re.DOTALL)
    for m in datas:
        date1 = re.findall(r'\d+', m)       # 正则提取出数字
        date2 = '-'.join(date1[:3])         # 拼接得到时间
        data_per = '.'.join(date1[-2:])     # 得到热度数据
        sheet.append([name, date2, data_per])
        logging.info([name, date2, data_per])

wb.save('language_data.xlsx')

