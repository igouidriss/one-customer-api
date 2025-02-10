<template>
  <div class="home">
    <div class="text-container">
      <h1 class="display-4" v-text="t$('home.title')"></h1>
      <p class="lead" v-text="t$('home.subtitle')"></p>

      <div>
        <div class="alert alert-success" v-if="authenticated">
          <span v-if="username" v-text="t$('home.logged.message', { username: username })"></span>
        </div>
      </div>
    </div>

    <!-- Image centrée sous le texte -->
    <span class="hipster img-fluid rounded"></span>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>
