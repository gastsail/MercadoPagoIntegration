/*
 *
 *  * Copyright (C) 2019 Gastón Luis Saillén.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

const functions = require('firebase-functions');
var mercadopago = require('mercadopago');
const admin = require('firebase-admin');
var serviceAccount = require("./melitest.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://melitest-5bc38.firebaseio.com"
});

// Configure MY_ACCESS_TOKEN with the access token provided by MercadoLibre API
mercadopago.configure({
  access_token: 'MY_ACCESS_TOKEN'
});

//Item Creation Trigger
exports.onItemCreation = functions.firestore
  .document('melitest/{itemId}').onCreate(async (snap, context) => {
    var db = admin.firestore();
    var preference = {}

    /*---- Spanish ----*
    *  const itemId = context.params.itemId --> Ya que solo tenemos un wildcard, podemos acceder directamente
    *  con la referencia, si tuvieramos un path como melitest/{userID}/compras/{itemId} podriamos acceder a cualquier 
    *  wildcar con context.params.userID , o context.params.itemId
    *  ¿ Cómo se maneja la idempotencia ? --> Ya que cada compra esta generada con un id unico, no existe la posibilidad
    *  de duplicar pagos, ya que al generar uno nuevo el id del producto cambia a uno nuevo aunque sea el mismo, generando asi solo un pedido de compra
    *---- Fin ----*/


    // Create the item preference to pay with item data and payer info
    try {
      const itemDataSnap = await snap.ref.get()
      var item = {
        title: itemDataSnap.data().title,
        quantity: itemDataSnap.data().quantity,
        description: itemDataSnap.data().description,
        currency_id: itemDataSnap.data().currency_id,
        unit_price: itemDataSnap.data().unit_price
      }

      var payer = {
        email: "demo@mail.com",
      }

      preference.items = [item]
      preference.payer = payer
    } catch (error) {
      return handleErrorToUser(error, snap)
    }

    // Generate the preference ID for a certain purchase
    try {
      const meliSnap = await mercadopago.preferences.create(preference)
      var string = JSON.stringify(meliSnap)
      var objectValue = JSON.parse(string)
      var itemRef = snap.ref;
      return itemRef.set({ preference_id: objectValue.response.id }, { merge: true })
    } catch (error) {
      return handleErrorToUser(error, snap)
    }

  });

// Inform about errors (these errors are for testing purposes, we should use StackDriver to log the errors 
// to ourselves and let a friendly user error message with this function)
async function handleErrorToUser(tError, snap) {
  console.log(tError)
  return await snap.ref.set({ transaction_error: tError }, { merge: true })
}




