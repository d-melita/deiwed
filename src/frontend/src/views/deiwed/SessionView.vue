<!-- show session details -->
<template>
    <v-card :flat="$store.getters.isMobile" class="table pb-4">
        <v-card-title>Sessão {{ session.id }}</v-card-title>
        <v-card-text>
            <!-- show date, speaker and theme of session -->
            <v-card-title class="text-center">
                <h2>Data: {{ session.date }}</h2>
            </v-card-title>
            <v-card-title class="text-center">
                <h2>Orador: {{ session.speaker }}</h2>
            </v-card-title>
            <v-card-title class="text-center">
                <h2>Tema: {{ session.theme }}</h2>
            </v-card-title>
            <!-- v-tabs for attendees and dishes -->
            <v-tabs v-model="tab" centered>
                <v-tab>Participantes</v-tab>
                <v-tab>Pratos</v-tab>
                <v-tab-item>
                    <v-card flat>
                        <v-card-text>
                            <v-data-table
                                :headers="headers_attendees"
                                :items="attendees_of_session"
                                :loading="loading"
                                single-select
                                locale="pt-PT"
                                no-data-text="Não existem participantes registados"
                                no-results-text="Nenhum participante corresponde aos critérios indicados"
                                sort-by="name"
                            >
                                <template v-slot:[`item.type`]="{ item }">
                                    <v-chip
                                        v-if="item.type === 'Professor'"
                                        color="purple"
                                        text-color="white"
                                    >
                                    Professor
                                    </v-chip>
                                    <v-chip v-else color="green" text-color="white"> Bolseiro </v-chip>
                                </template>
                                <template v-slot:[`item.actions`]="{ item }">
                                    <v-btn
                                        color="error"
                                        @click="removeAttendee(item)"
                                    >
                                        <v-icon>mdi-delete</v-icon>
                                    </v-btn>
                                </template>
                                <template v-slot:[`item.details`]="{ item }">
                                    <!-- small button to see session details-->
                                    <v-btn
                                        x-small
                                        color="primary"
                                        :to="`/attendee${item.id}/sessions`"
                                    >
                                        <v-icon>mdi-eye</v-icon>
                                    </v-btn>
                                </template>
                            </v-data-table>
                            <v-dialog v-model="dialog" max-width="500px">
                                <template v-slot:activator="{ on }">
                                    <v-btn color="primary" dark class="mb-2" v-on="on">
                                        <v-icon left>mdi-plus</v-icon> Adicionar Participante
                                    </v-btn>
                                </template>
                                <v-card>
                                    <v-card-title>
                                        <span class="headline">Adicionar Participante</span>
                                    </v-card-title>
                                    <v-card-text>
                                        <v-container>
                                            <v-row>
                                                <v-col cols="12">
                                                    <v-autocomplete
                                                        v-model="attendee"
                                                        :items="attendees"
                                                        item-text="name"
                                                        item-value="id"
                                                        label="Participante"
                                                        return-object
                                                        outlined
                                                        dense
                                                        clearable
                                                        :loading="loading"
                                                        :no-data-text="noDataText"
                                                        :no-filter="!search"
                                                        :loading-text="loadingText"
                                                        :error-messages="errors.attendee"
                                                    ></v-autocomplete>
                                                </v-col>
                                            </v-row>
                                        </v-container>
                                    </v-card-text>
                                    <v-card-actions>
                                        <v-spacer></v-spacer>
                                        <v-btn color="blue darken-1" text @click="close">Cancelar</v-btn>
                                        <v-btn color="blue darken-1" text @click="save">Salvar</v-btn>
                                    </v-card-actions>
                                </v-card>
                                </v-dialog>
                        </v-card-text>
                    </v-card>
                </v-tab-item>
                <v-tab-item>
                    <v-card flat>
                        <v-card-text>
                            <v-data-table
                                :headers="headers_dishes"
                                :items="dishes"
                                :loading="loading"
                                single-select
                                locale="pt-PT"
                                no-data-text="Não existem pratos registados"
                                no-results-text="Nenhum prato corresponde aos critérios indicados"
                                sort-by="name"
                            >
                            <!-- type ? vegetarian: true, normal: false -->
                            <template v-slot:[`item.vegetarian`]="{ item }">
                                <v-chip
                                    v-if="item.vegetarian === true"
                                    color="green"
                                    text-color="white"
                                >
                                    Vegetariano
                                </v-chip>
                                <v-chip v-else color="red" text-color="white"> Normal </v-chip>
                            </template>
                            </v-data-table>
                            <!-- v-btn no add dishes if dishes == [] -->
                            <v-btn
                                color="primary"
                                dark
                                class="mb-2"
                                :to="`/session${session.id}-make-order`"
                                v-if="dishes.length == 0"
                            >
                                <v-icon left>mdi-plus</v-icon> Fazer Encomenda
                            </v-btn>
                            <v-btn
                                color="primary"
                                dark
                                class="mb-2"
                                :to="`/session${session.id}-edit-order`"
                                v-if="dishes.length > 0"
                            >
                                <v-icon left>mdi-plus</v-icon> Editar Encomenda
                            </v-btn>
                            <v-btn
                                color="primary"
                                dark
                                class="mb-2"
                                @click="removeOrder()"
                                v-if="dishes.length > 0"
                            >
                                <v-icon left>mdi-plus</v-icon> Remover Encomenda
                            </v-btn>
                        </v-card-text>
                    </v-card>
                </v-tab-item>
            </v-tabs>
        </v-card-text>
        <!-- go to sessions button on top right -->
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text :to="`/sessions`">Todas as Sessões</v-btn>
        </v-card-actions>
    </v-card>
</template>

<script lang="ts">
import SessionDto from '@/models/deiwed/SessionDto';
import SessionAttendeesDto from '@/models/deiwed/SessionAttendeesDto';
import AttendeeDto from '@/models/deiwed/AttendeeDto';
import RemoteServices from '@/services/RemoteServices';
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class SessionView extends Vue {
    session: SessionDto = new SessionDto();
    attendee: AttendeeDto = new AttendeeDto();
    attendees_id: SessionAttendeesDto[] = [];
    attendees_of_session: AttendeeDto[] = [];
    attendees: AttendeeDto[] = [];
    dishes: any[] = [];
    dialog = false;
    search = "";
    loading = false;
    loadingText = "A procurar...";
    noDataText = "Não foram encontrados participantes";
    errors: any = {};

    // v-tabs
    tab = null;

    // attendees table
    headers_attendees = [
        { text: 'Nome', value: 'name' },
        { text: 'IST ID', value: 'istId', sortable: true, filterable: true },
        { text: 'Tipo', value: 'type', sortable: true, filterable: false },
        { text: 'Ações', value: 'actions', sortable: false },
        { text: 'Ver Sessões', value: 'details', sortable: false, filterable: false },
    ];

    // dishes table
    headers_dishes = [
        { text: 'Nome', value: 'name', sortable: true, filterable: true },
        { text: 'Preço', value: 'unitPrice', sortable: true, filterable: true },
        { text: 'Descrição', value: 'description', sortable: true, filterable: true },
        { text: 'Opção', value: 'vegetarian', sortable: true, filterable: true },
    ];

    async mounted() {
        this.loading = true;
        this.session = await RemoteServices.getSession(parseInt(this.$route.params.id));
        this.attendees_id = await RemoteServices.getAllSessionAttendees(this.session.id);
        // for loop to get all attendees
        for (let i = 0; i < this.attendees_id.length; i++) {
            this.attendees_of_session.push(await RemoteServices.getAttendee(this.attendees_id[i].attendeeId));
        }
        await this.getAttendees();
        this.dishes = await RemoteServices.getSessionDishes(this.session.date);
        if (this.dishes.length > 0) {
            this.dishes = [this.dishes[0].normalOption, this.dishes[0].vegetarianOption];
        }
        this.loading = false;
    }

    async removeAttendee(attendee: AttendeeDto) {
        this.loading = true;
        await RemoteServices.removeAttendeeFromSession(this.session.id, attendee.id);
        this.loading = false;
        // refresh page to garantee that the attendee is removed from table and from attendees_of_session
        this.$router.go(0);
    }

    async getAttendees() {
        this.loading = true;
        this.attendees = await RemoteServices.getAttendees();
        // remove attendees that are already in the session
        for (let i = 0; i < this.attendees_of_session.length; i++) {
            this.attendees = this.attendees.filter((a) => a.id !== this.attendees_of_session[i].id);
        }
        this.loading = false;
    }

    async save(): Promise<void> {
        this.errors = {};
        if (this.attendee.id === 0) {
            this.errors.attendee = ["O campo participante é obrigatório"];
            return;
        }
        const sessionAttendee = new SessionAttendeesDto();
        sessionAttendee.attendeeId = this.attendee.id;
        sessionAttendee.sessionId = this.session.id;
        await RemoteServices.addAttendeeToSession(sessionAttendee);
        this.close();
        // refresh page
        this.$router.go(0);
    }

    close() {
        this.dialog = false;
        this.attendee = new AttendeeDto();
    }

    removeOrder() {
        this.loading = true;
        RemoteServices.removeOrder(this.session.date);
        this.loading = false;
        // refresh page
        this.$router.go(0);
    }
}
</script>