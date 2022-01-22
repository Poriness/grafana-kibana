<template>
  <div class="container">
    <div v-if="!currentUser">
        <div class="jumbotron">
          <h1 class="display-3">Hello!</h1>
          <p class="lead">Welcome to our dietitian center!</p>
          <hr class="my-4">
          <p> If you are new, please register here</p>
          <p class="lead">
            <a class="btn btn-primary btn" href="/register" role="button">Register</a>
          </p>
          <p> Want to login?</p>
          <p class="lead">
            <a class="btn btn-primary btn" href="/login" role="button">Login</a>
          </p>
        </div>

    </div>
    <div v-if="currentUser">
        <div class="jumbotron">
          <h1 class="display-3">Hello {{ this.content.firstname }} {{ this.content.lastname }}!</h1>
          <p class="lead">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam mi magna, vestibulum ut erat
            id, elementum rhoncus libero. Donec viverra massa vel arcu porta, ac faucibus est faucibus. </p>
          <hr class="my-4">
          <p> Nam iaculis ultrices neque, non rhoncus nisi consectetur et. Nulla et vehicula sem. Nulla ultricies
            sodales
            laoreet. Pellentesque libero nisl, pretium eget purus faucibus, feugiat aliquet metus.</p>
          <p class="lead">
            <a class="btn btn-primary btn-lg" href="#" role="button">More</a>
          </p>
        </div>
    </div>
  </div>
</template>

<script>

import UserService from "../services/user.service";

export default {
  name: "Home",
  data() {
    return {
      content: "",
    };
  },

  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },

  },

  mounted() {
    if(this.currentUser){
    UserService.getUserinfo(this.currentUser.id).then(
        (response) => {
          this.content = response;
        },
        (error) => {
          this.content =
              (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
              error.message ||
              error.toString();
        }
    );}
  },
};
</script>
