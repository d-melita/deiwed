<!-- edit attendee page - edit attendee with name, istId, type -->
<template>
    <v-card>
        <v-card-title>
        <h1 class="display-1">Editar Participante</h1>
        </v-card-title>
        <!-- v-card to show attendee -->
        <v-card-text>
        <v-form>
            <!-- name not empty -->
            <v-card-title class="text-center">
                <h3>Nome Atual: {{ current_attendee.name }}</h3>
            </v-card-title>
            <v-text-field
            v-model="name"
            label="Editar Nome (Primeiro e Último)"
            ></v-text-field>
            <v-card-title class="text-center">
                <h3>IST ID Atual: {{ current_attendee.istId }}</h3>
            </v-card-title>
            <v-text-field
            v-model="istId"
            label="Editar IST ID (istXXXXXX)"
            ></v-text-field>
            <v-card-title class="text-center">
                <h3>Tipo Atual: {{ current_attendee.type }}</h3>
            </v-card-title>
            <v-select
            v-model="type"
            :items="['Professor', 'Bolseiro']"
            label="Tipo"
            ></v-select>
        </v-form>
        <!-- button bottom right -->
        <v-btn color="primary" class="" @click="editAttendee">
            <v-icon left>mdi-plus</v-icon> Editar Participante
        </v-btn>
        <!-- cancel button bottom left -->
        <v-btn color="error" class="" :to="`/attendees`">
            <v-icon left>mdi-close</v-icon> Cancelar
        </v-btn>
        </v-card-text>
    </v-card>
</template>

<script lang="ts">
import AttendeeDto from '@/models/deiwed/AttendeeDto';
import RemoteServices from '@/services/RemoteServices';
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class editAttendee extends Vue {
  id: number = 0;
  name: string = '';
  istId: string = '';
  type: string = '';
  current_attendee: AttendeeDto = new AttendeeDto();

  async mounted() {
    this.current_attendee = await RemoteServices.getAttendee(parseInt(this.$route.params.id));
  }

  async editAttendee() {
    const attendee: AttendeeDto = {
        id: parseInt(this.$router.currentRoute.params.id),
        name: this.name,
        istId: this.istId,
        type: this.type,
    };
    if (attendee.name == '') {
        attendee.name = this.current_attendee.name;
    }
    if (attendee.istId == '') {
        attendee.istId = this.current_attendee.istId;
    }
    if (attendee.type == '') {
        attendee.type = this.current_attendee.type;
    }
    await RemoteServices.editAttendee(attendee);
    this.$router.push('/attendees');
  }
}
</script>