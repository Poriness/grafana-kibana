<template>
  <div class="dropdown">
    <input @click="onClickMethod"
           v-if="Object.keys(selectedItem).length === 0 || clear"
           ref="dropdowninput"
           v-model.trim="inputValue"
           class="form-control me-sm-2"
           type="text"
           :placeholder=this.placeholder :disabled=this.disable
    />
    <div v-else @click="resetSelection" class="dropdown-selected">
      {{ selectedItem.firstname }} {{ selectedItem.lastname }}
    </div>
    <div v-show="this.showList || (inputValue && apiLoaded) || showDropdowns" class="dropdown-menu show" data-bs-popper="none">
      <div
          @click="selectItem(item)"
          v-show="itemVisible(item)"
          v-for="item in itemList"
          :key="item.id"
          class="dropdown-item"
      >
        {{ item.firstname }} {{ item.lastname }}
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import authHeader from "../services/auth-header";

export default {
  name: "DropdownList",
  props: {
    url: String,
    placeholder: String,
    clear: Boolean,
    showDropdowns: Boolean,
    disable: Boolean
  },

  data() {
    return {
      selectedItem: {},
      inputValue: '',
      itemList: [],
      apiLoaded: false,
      showList: false,
      apiUrl: ""
    }
  },

  mounted() {
    this.getList()
  },
  methods: {
    resetSelection() {
      this.selectedItem = {}
      this.$nextTick(() => this.$refs.dropdowninput.focus())
      this.$emit('on-item-reset')
      this.showList = false
    },
    selectItem(theItem) {
      this.selectedItem = theItem
      this.inputValue = ''
      this.$emit('on-item-selected', theItem)
      this.showList = false
    },
    itemVisible(item) {
      let currentName = item.firstname.toLowerCase() + " " + item.lastname.toLowerCase();
      let currentInput = this.inputValue.toLowerCase()
      return currentName.includes(currentInput)
    },
    getList() {
      if (this.url === "") {
        this.apiLoaded = false;
      } else {
        axios.get(this.url, { headers: authHeader() }).then((response) => {
          this.itemList = response.data
          this.apiLoaded = true
        })
        this.apiUrl = this.url;
      }
    },

    onClickMethod() {
      if (this.apiUrl !== this.url) {
        this.getList();
      }
      this.showList = !this.showList;
    }


  },
}
</script>

<style scoped>
/*.dropdown {*/
/*  position: relative;*/
/*  width: 100%;*/
/*  max-width: 400px;*/
/*  margin: 0 auto;*/
/*}*/

.dropdown-input,
.dropdown-selected {
  width: 100%;
  padding: 10px 16px;
  border: 1px solid transparent;
  background: #fff;
  line-height: 1.5em;
  outline: none;
  border-radius: 8px;
}

.dropdown-input:focus,
.dropdown-selected:hover {
  background: #fff;
  border-color: #e2e8f0;
}

.dropdown-input::placeholder {
  opacity: 0.7;
}

.dropdown-selected {
  font-weight: bold;
  cursor: pointer;
}

/*.dropdown-list {*/
/*  position: absolute;*/
/*  width: 100%;*/
/*  max-height: 500px;*/
/*  margin-top: 4px;*/
/*  overflow-y: auto;*/
/*  background: #ffffff;*/
/*  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1),*/
/*  0 4px 6px -2px rgba(0, 0, 0, 0.05);*/
/*  border-radius: 8px;*/
/*}*/

/*.dropdown-item {*/
/*  display: flex;*/
/*  width: 100%;*/
/*  padding: 11px 16px;*/
/*  cursor: pointer;*/
/*}*/

/*.dropdown-item:hover {*/
/*  background: #edf2f7;*/
/*}*/
</style>