<template>
  <div class="home">
    <div class="home-text">
      <h1>DEI @ WEDNESDAYS</h1>
      <h2>Sessões semanais com palestras e almoço!</h2>
    </div>
    <!-- buttons to attendees, dishes and sessions -->
    <div class="att-button">
      <a href="/attendees">
        <button>Participantes</button>
      </a>
    </div>
    <div class="dish-button">
      <a href="/dishes">
        <button>Pratos</button>
      </a>
    </div>
    <div class="sess-button">
      <a href="/sessions">
        <button>Sessões</button>
      </a>
    </div> 
  </div>
</template>

<!-- style with css home.css -->
<style src="../assets/css/home.css"></style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class HomeView extends Vue {
}
</script>