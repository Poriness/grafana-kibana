<template>
  <div class="container ">
      <div class="jumbotron">
        <div class=" card-header">
          <h3>
            <strong>{{ content.firstname }} {{ content.lastname }}</strong> Profile
          </h3>
        </div>
        <div class="card-body">
          <p>
            <strong>Id:</strong>
            {{ currentUser.id }}
          </p>
          <p>
            <strong>username:</strong>
            {{ currentUser.username }}
          </p>
          <p>
            <strong>Authorities:</strong>
            {{ currentUser.role }}
          </p>
        </div>
      </div>
  </div>
</template>

<script>
import UserService from "../services/user.service";

export default {
  name: 'Profile',

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
    if (!this.currentUser) {
      this.$router.push('/login');
    }

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
    );

  },


}
;
</script>