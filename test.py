import threading

inventory = {
    "apple": 10,
    "banana": 5,
    "orange": 8
}


def purchase(item, quantity):
    if item not in inventory:
        print(f"{item} not found")
        return

    stock = inventory[item]

    if stock >= quantity:
        stock -= quantity
        inventory[item] = stock
        print(f"Purchased {quantity} {item}")
    else:
        print("Not enough stock")


def process_orders(orders):
    threads = []

    for order in orders:
        t = threading.Thread(
            target=lambda: purchase(order["item"], order["quantity"])
        )
        threads.append(t)
        t.start()

    for t in threads:
        t.join()


orders = [
    {"item": "apple", "quantity": 3},
    {"item": "banana", "quantity": 2},
    {"item": "orange", "quantity": 4},
    {"item": "apple", "quantity": 5},
]

process_orders(orders)

print(inventory)
