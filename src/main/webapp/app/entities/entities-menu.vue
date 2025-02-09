<template>
  <div>
    <b-dropdown-item to="/one-customer-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.oneCustomerMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/golden-record-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.goldenRecordMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/payload-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.payloadMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/email-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.emailMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/phone-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.phoneMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/address-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.addressMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/cancelled-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.cancelledMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/source-reference-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.sourceReferenceMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/hotel-reservation-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.hotelReservationMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/restoration-reservation-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.restorationReservationMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/expenses-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.expensesMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/expense-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.expenseMySuffix')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/metadata-my-suffix">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.metadataMySuffix')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
