# espresso-guide

This app is created to demonstrate different approaches of android automation tests.

There are:
1. Unit tests with Robolectric and Mockito.
2. Simple integration test.
3. Simple and advanced espresso tests.

## Ultron framework

All advanced actions and assertions in this sample project are executed using [Ultron](https://github.com/alex-tiurin/ultron) framework.

## Deep dive into espresso.  

**It will be really useful if you read this [README](https://github.com/alex-tiurin/ultron) firstly**

In **EspressoGuide** you can find:

1. A page object approach for Espresso tests (look at the description of page object classes [here](https://github.com/alex-tiurin/espresso-guide/tree/master/app/src/androidTest/java/com/atiurin/espressoguide/pages)).
2. A new style of Espresso test using [Ultron](https://github.com/alex-tiurin/ultron) . 
The best example of how these tests looks like you can find in class [DemoEspressoTest](https://github.com/alex-tiurin/espresso-guide/blob/master/app/src/androidTest/java/com/atiurin/espressoguide/tests/DemoEspressoTest.kt)
3. A new great approach of how to work with RecyclerView lists (look inside page object classes again). 
4. A new safe and simple way of using IdlingResources (look at [BaseTest](https://github.com/alex-tiurin/espresso-guide/blob/master/app/src/androidTest/java/com/atiurin/espressoguide/tests/BaseTest.kt), 
[IdlingUtils](https://github.com/alex-tiurin/espresso-guide/blob/master/app/src/androidTest/java/com/atiurin/espressoguide/framework/IdlingUtils.kt), and all things inside [idlingresources package](https://github.com/alex-tiurin/espresso-guide/tree/master/app/src/main/java/com/atiurin/espressoguide/idlingresources)).
5. How to configure allure report for a project.
6. How to find UI elements with **view.tag** value. This way of searching an element  on the screen is really useful when resource id, content description and text are not applicable.








