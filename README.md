# webhooks_vip_proxy

This is a dropwizard application which has API's which I am using to create webhooks for Twitter, Instagram, and Facbook page and use it to receive webhook events via this API.

I have deployed this API in heroku.

Corresponding heroku app - https://dashboard.heroku.com/apps/dropwizard-app-scs-webhooks

The heroku app uses the Procfile to depict how to run this java app

Base url of the app - https://dropwizard-app-scs-webhooks.herokuapp.com/

The details about endpoints mentioned in  the below table.

Data Source | Endpoint |Method | Full Url 
--- | --- | --- | --- 
Twitter | /twitter/events |GET  | https://dropwizard-app-scs-webhooks.herokuapp.com/twitter/events 
Instagram | /instagram/events |GET  | https://dropwizard-app-scs-webhooks.herokuapp.com/instagram/events
Facebook Page | /fb_page/events |GET     | https://dropwizard-app-scs-webhooks.herokuapp.com/fb_page/events