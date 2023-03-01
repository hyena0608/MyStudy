# Vue.js 코딩 공작소 🚀

<br>
<br>

### 폼과 입력

#### v-model
- 입력과 폼 바인딩

#### v-model.number
- v-model 지시자 값으 숫자로 자동 타입 변환

#### v-model.trim
- 자동 여백 없애기

#### v-model.lazy
- on change 이벤트에 동기화

<br>
<br>

#### v-bind
- HTML 속성 바인딩

```HTML
<input v-model="something"> 은
<input v-bind:"something" v-on:input="something=$event.target"> 이다.
```