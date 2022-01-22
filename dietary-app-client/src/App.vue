<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
      <a href="/home" class="navbar-brand">The Dietitian Center</a>
      <button class="navbar-toggler" type="button"
              data-toggle="collapse" data-target="#navbarColor01"
              aria-controls="navbarColor01" aria-expanded="false"
              aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <router-link to="/home" class="nav-link">
              <font-awesome-icon icon="home"/>
              Home
            </router-link>
          </li>
          <li v-if="showClientBoard" class="nav-item">
            <router-link :to="{name: 'client-calendar'}" class="nav-link">Calendar</router-link>
          </li>
          <li v-if="showCustomerServiceBoard || showAdminBoard" class="nav-item">
            <router-link :to="{name: 'customer-service-calendar'}" class="nav-link">Calendar</router-link>
          </li>
          <li v-if="showDietitianBoard" class="nav-item">
            <router-link :to="{name: 'dietitian-calendar'}" class="nav-link">Calendar</router-link>
          </li>
          <li v-if="showCustomerServiceBoard || showAdminBoard" class="nav-item">
            <router-link :to="{name: 'cs-register'}" class="nav-link">Registration</router-link>
          </li>
          <li v-if="showCustomerServiceBoard || showAdminBoard" class="nav-item">
            <router-link :to="{name: 'cs-activation'}" class="nav-link">Activation</router-link>
          </li>
          <li v-if="showCustomerServiceBoard || showAdminBoard" class="nav-item">
            <router-link :to="{name: 'assign'}" class="nav-link">Assign Dietitian</router-link>
          </li>
        </ul>
      </div>

      <div v-if="!currentUser" class="navbar-nav my-2 my-lg-0">
        <li class="nav-item">
          <router-link to="/register" class="nav-link">
            <font-awesome-icon icon="user-plus"/>
            Sign Up
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/login" class="nav-link">
            <font-awesome-icon icon="sign-in-alt"/>
            Login
          </router-link>
        </li>
      </div>

      <div v-if="currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/profile" class="nav-link">
            <font-awesome-icon icon="user"/>
            {{ currentUser.username }}
          </router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" @click.prevent="logOut">
            <font-awesome-icon icon="sign-out-alt"/>
            LogOut
          </a>
        </li>
      </div>
    </div>
  </nav>
  <div>
    <router-view/>
  </div>
</template>

<script>

export default {
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },

    currentUserId() {
      return this.currentUser['id']
    },

    showAdminBoard() {
      if (this.currentUser && this.currentUser['role']) {
        return this.currentUser['role'].includes('ADMIN');
      }
      return false;
    },

    showCustomerServiceBoard() {
      if (this.currentUser && this.currentUser['role']) {
        return this.currentUser['role'].includes('CUSTOMERSERVICE');
      }
      return false;
    },

    showClientBoard() {
      if (this.currentUser && this.currentUser['role']) {
        return this.currentUser['role'].includes('CLIENT');
      }
      return false;
    },


    showDietitianBoard() {
      if (this.currentUser && this.currentUser['role']) {
        return this.currentUser['role'].includes('DIETITIAN');
      }
      return false;
    }
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    }
  }
};
</script>

<style>

.jumbotron {
  padding: 2rem 1rem;
  margin: 2rem;
  background-color: #f7f7f9;
  border-radius: 0.6rem;
}


</style>