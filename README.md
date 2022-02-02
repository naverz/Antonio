
# Antonio
Android library for the adapter view (RecyclerView, ViewPager, ViewPager2)

* Free from implementation of the adapter's boilerplate code!
* Free from implementation of the view holder with data-binding (Two-way binding OK)!
* ViewPager, ViewPager2, Paging2, Paging3 supported!
* Manage the recycler view state on your view model!
* There is no things to learn, if you already know about adapter views (RecyclerView, ViewPager, ViewPager2)!
     
# Install
## Without data binding
```groovy
dependencies {
    def antonioVersion = '1.0.6-alpha'
    def antonioAnnotationVersion = '0.0.4-alpha'
  
    implementation "io.github.naverz:antonio:$antonioVersion"

    implementation "io.github.naverz:antonio-annotation:$antonioAnnotationVersion"

    //For java
    annotationProcessor "io.github.naverz:antonio-compiler:$antonioAnnotationVersion"

    //For kotlin
    kapt "io.github.naverz:antonio-compiler:$antonioAnnotationVersion"

    //For paging2
    implementation "io.github.naverz:antonio-paging2:$antonioVersion"

    //For paging3
    implementation "io.github.naverz:antonio-paging3:$antonioVersion"
}
```
## With data binding
```groovy
dependencies {
    def antonioVersion = '1.0.6-alpha'
    def antonioAnnotationVersion = '0.0.4-alpha'
  
    implementation "io.github.naverz:antonio-databinding:$antonioVersion"

    implementation "io.github.naverz:antonio-annotation:$antonioAnnotationVersion"

    //For java
    annotationProcessor "io.github.naverz:antonio-compiler:$antonioAnnotationVersion"

    //For kotlin
    kapt "io.github.naverz:antonio-compiler:$antonioAnnotationVersion"

    //For paging2
    implementation "io.github.naverz:antonio-databinding-paging2:$antonioVersion"

    //For paging3
    implementation "io.github.naverz:antonio-databinding-paging3:$antonioVersion"
}
```
# Basic Usage
- Implement `AntonioBindingModel` to bind model on your **view holder layout xml**. 
<p align="center">
  <img src ="https://user-images.githubusercontent.com/15243641/134372911-2c71d7b8-9abb-4757-8991-46b6a3057763.png" width ="90">
</p>

```kotlin
data class ContentSmallModel(
    val id: String,
    @DrawableRes val iconRes: Int,
    val price: Int,
    val onClick: (id: String) -> Unit,
    val onLongClick: (id: String) -> Boolean,
    val selectedIds: LiveData<Set<String>>
) : AntonioBindingModel {
    // Layout id to be inflated
    override fun layoutId(): Int = R.layout.view_holder_content_small
    // Variable id in XML to be bind on bind view holder
    // (e.g., onBindViewHolder in ViewHolder, onViewCreated in Fragment).
    override fun bindingVariableId(): Int = BR.model
}
```
- Create your view holder layout xml with the AntonioBindingModel you implemented.
`view_holder_content_small.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    >
    <data>
        <variable
            name="model"
            type="io.github.naverz.antonio.sample.ContentSmallModel"
        />
    </data>

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:gravity="center_horizontal"
        android:onClick="@{()->model.onClick.invoke(model.id)}"
        android:onLongClick="@{(view)->model.onLongClick.invoke(model.id)}"
        android:orientation="vertical"
        android:padding="5dp"
        >
        <!-- ... -->
    </LinearLayout>
</layout>
```
- Declare `AntonioAdapter` (or `AntonioListAdapter`) and Set adapter with your data to RecyclerView.
```kotlin
   private fun initAdapter(){
       // You also can specify the type of Antonio model for the adapter, if you don't need various view types.
       // e.g., AntonioAdapter<ContentSmallModel>()
        binding.recyclerView.adapter =
            AntonioAdapter<AntonioModel>().apply { currentList = viewModel.antonioModels }
        // Don't forget to set the layout manager to your recycler view :)
        binding.recyclerView.layoutManager = GridLayoutManager(context,4)    
        viewModel.onDataSetChanged.observe(this) {
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
   }
  
```
<p align="center">
  <img src ="https://user-images.githubusercontent.com/15243641/134373020-e7b1f23d-1b10-406c-b180-63acdcb27215.png" width ="200">
</p>

## LifecycleOwner will be injected automatically!
LifecycleOwner, which is nearest from the view, will be automatically injected on All of XML inflated from `AutoBindingModel` in order to use LiveData for two-way binding.

<p align="center">
<img src="https://user-images.githubusercontent.com/15243641/134387612-8217d441-6a1e-4e41-98f4-5368ca987890.png" width="300">
</p>

# Awesome wiki! 
- [Additional tips for Data binding](https://github.com/naverz/Antonio/wiki/Additional-tips-for-Data-binding)
    - [With Data Binding](https://github.com/naverz/Antonio/wiki/Additional-tips-for-Data-binding#with-data-binding)
    - [How to inject data when the view is created (e.g, onCreateViewHolder).](https://github.com/naverz/Antonio/wiki/Additional-tips-for-Data-binding#with-data-binding)
    - [LifecycleOwner](https://github.com/naverz/Antonio/wiki/Additional-tips-for-Data-binding#lifecycleowner)
- [Usage without data binding](https://github.com/naverz/Antonio/wiki/Usage-without-data-binding)
- [Additional component for Antonio](https://github.com/naverz/Antonio/wiki/Additional-component-for-antonio)
    - [For RecyclerView](https://github.com/naverz/Antonio/wiki/Additional-component-for-antonio#for-recyclerview)
    - [For ViewPager](https://github.com/naverz/Antonio/wiki/Additional-component-for-antonio#for-viewpager)
    - [For ViewPager2](https://github.com/naverz/Antonio/wiki/Additional-component-for-antonio#for-viewpager2)
    - [For Adapter](https://github.com/naverz/Antonio/wiki/Additional-component-for-antonio#for-adapter)
- [Plug-in](https://github.com/naverz/Antonio/wiki/Plug-in)
    - [ViewPager](https://github.com/naverz/Antonio/wiki/Plug-in#viewpager)
    - [ViewPager2](https://github.com/naverz/Antonio/wiki/Plug-in#viewpager2)
    - [Paging 2](https://github.com/naverz/Antonio/wiki/Plug-in#paging2)
    - [Paging-3](https://github.com/naverz/Antonio/wiki/Plug-in#paging3)
- [AdapterView state (RecyclerView, ViewPager)](https://github.com/naverz/Antonio/wiki/AdapterView-state-(RecyclerView,-ViewPager))
    -  [How to use](https://github.com/naverz/Antonio/wiki/AdapterView-state-(RecyclerView,-ViewPager)#how-to-use)
    -  [Why AdapterView cannot be expressed as a state in your view model.](https://github.com/naverz/Antonio/wiki/AdapterView-state-(RecyclerView,-ViewPager)#why-adapterview-cannot-be-expressed-as-a-state-in-your-view-model)
    -  [Dependency injection](https://github.com/naverz/Antonio/wiki/AdapterView-state-(RecyclerView,-ViewPager)#dependency-injection)
    -  [For test code](https://github.com/naverz/Antonio/wiki/AdapterView-state-(RecyclerView,-ViewPager)#for-test-code)

# Sample
[The sample module](./sample) is available!

<img src="https://user-images.githubusercontent.com/15243641/134202280-4fb1787d-78a6-4316-9b9f-5778fcc68679.gif" width="270" />

# License
```
Antonio
Copyright (c) 2021-present NAVER Z Corp.

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

