# ParcelableTest
A library making it easy to verify that your `Parcelable` implementations behave as expected when they are parceled and un-parceled.

## Usage
Extend `ParcelableTest` and provide an instance of your `Parcelable` as well as its `CREATOR`:
```java
public class MyParcelableTest extends ParcelableTest<MyParcelable> {

    @NonNull
    @Override
    protected MyParcelable newItem() {
        return new MyParcelable("Member variable value");
    }

    @NonNull
    @Override
    protected Parcelable.Creator<MyParcelable> itemCreator() {
        return MyParcelable.CREATOR;
    }
}
```

When you run `MyParcelableTest`, a test will parcel your provided instance and un-parcel it, and then assert that the 2 objects are equal before and
after.

## License
```
Copyright 2019 Backbase R&D B.V.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
