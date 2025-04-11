document.addEventListener('alpine:init', () => {
    Alpine.data(
        'initData', () => ({
            cart: {
                items: [],totalAmount:0
            },
            orderForm: {
                customer: {
                    name: "Victor",
                    email: "vicktord007@gmal.com",
                    phone: "09063453187"
                },
                deliveryAddress: {
                    addressLine1: "KPHB",
                    addressLine2: "IIB",
                    city: "Ibadan",
                    state: "oyo",
                    zipCode: "200223",
                    country: "Nigeria"
                }
            },
            init() {
                updateCartItemCount();
                this.loadCart();
                this.cart.totalAmount = getCartTotal();
            },
            loadCart(){
                this.cart = getCart()
            },
            updateItemQuantity(code, quantity){
                updateProductQuantity(code, quantity);
                this.loadCart();
                this.cart.totalAmount = getCartTotal();
            },
            removeCart() {
                deleteCart()
            },
            createOrder(){
                let order = Object.assign({}, this.orderForm, {items: this.cart.items});

                $.ajax(
                    {
                        url: '/api/orders',
                        type: "POST",
                        dataType: "json",
                        contentType: "application/json",
                        data: JSON.stringify(order),
                        success: (response) => {
                            this.removeCart();
                            window.location = "/orders/" + response.orderNumber;
                        }, error: (err) => {
                            console.log("Order Creation Error", err)
                            alert("Order creation failed")
                        }
                    }

                )
            }
        })
    )
})